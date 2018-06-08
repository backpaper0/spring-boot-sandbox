package com.example.resttemplateexample;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FileUploaderControllerTest {

    @LocalServerPort
    private int port;

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Test
    public void multiPart() {

        //--------------------------------------------------------------------------------
        //マルチパートに含めるファイルのエンティティを組み立てる

        //ヘッダ
        final HttpHeaders headers = new HttpHeaders();

        final ContentDisposition cd = ContentDisposition.builder("form-data")
                .name("hoge")
                .filename("hoge.txt", StandardCharsets.UTF_8)
                .build();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, cd.toString());
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
        //headers.add(HttpHeaders.TRANSFER_ENCODING, "binary");

        //エンティティボディ
        //ファイルから読み込むならPathResource(、FileSystemResource)
        //InputStreamから読み込むならInputStreamResource
        //クラスパスから読み込むならClassPathResource
        final Resource resource = new ByteArrayResource("Hello, world!".getBytes());
        final HttpEntity<?> hoge = new HttpEntity<>(resource, headers);

        //--------------------------------------------------------------------------------
        //リクエストボディを組み立てる

        //マルチパートも通常(URLエンコード)のフォームもMultiValueMapで良さげ
        //どちらでもFormHttpMessageConverterに処理される
        final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        //組み立てたファイルのエンティティを追加する
        body.add("hoge", hoge);

        //--------------------------------------------------------------------------------
        //リクエストを送信する

        //RestTemplateをインスタンス化
        //デフォルトだとByteArrayOutputStreamに全て書き出してから実際のレスポンスへコピーするっぽい
        //メモリ大事にしたい場合はbufferRequestBodyをfalseにした方が良さそう
        //その場合はContent-Lengthが事前に分からないのでchunkで送信されることになる
        final SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        //プロダクションコードの場合は@Beanでコンポーネント登録すると良い
        final RestTemplate restTemplate = new RestTemplate(requestFactory);

        final URI uri = UriComponentsBuilder
                .fromHttpUrl("http://localhost:{port}/upload")
                .build(port);

        final RequestEntity<?> requestEntity = RequestEntity
                .post(uri)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body);

        final ResponseEntity<String> responseEntity = restTemplate
                .exchange(requestEntity, String.class);

        //--------------------------------------------------------------------------------
        //Assertion

        outputCapture.expect(containsString("name=hoge"));
        outputCapture.expect(containsString("filename=hoge.txt"));
        outputCapture.expect(containsString("content-type=text/plain"));

        assertEquals("Hello, world!", responseEntity.getBody());
    }

}
