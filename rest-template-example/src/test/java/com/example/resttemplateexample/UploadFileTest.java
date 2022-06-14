package com.example.resttemplateexample;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UploadFileTest {

	@LocalServerPort
	int port;

	@Test
	void multiPart() {

		//--------------------------------------------------------------------------------
		//マルチパートに含めるファイルのエンティティを組み立てる

		//ヘッダ
		HttpHeaders headers = new HttpHeaders();

		ContentDisposition cd = ContentDisposition.builder("form-data")
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
		Resource resource = new ByteArrayResource("Hello, world!".getBytes());
		HttpEntity<?> hoge = new HttpEntity<>(resource, headers);

		//--------------------------------------------------------------------------------
		//リクエストボディを組み立てる

		//マルチパートも通常(URLエンコード)のフォームもMultiValueMapで良さげ
		//どちらでもFormHttpMessageConverterに処理される
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		//組み立てたファイルのエンティティを追加する
		body.add("hoge", hoge);

		//--------------------------------------------------------------------------------
		//リクエストを送信する

		//RestTemplateをインスタンス化する
		//プロダクションコードの場合は@Beanでコンポーネント登録すると良い
		RestTemplate restTemplate = new RestTemplate();

		//RequestFactoryを設定する(任意)
		//デフォルトだとByteArrayOutputStreamに全て書き出してから実際のレスポンスへコピーするっぽい
		//メモリ大事にしたい場合はbufferRequestBodyをfalseにした方が良さそう
		//その場合はContent-Lengthが事前に分からないのでchunkで送信されることになる
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setBufferRequestBody(false);
		restTemplate.setRequestFactory(requestFactory);

		URI uri = UriComponentsBuilder
				.fromHttpUrl("http://localhost:{port}")
				.path("/upload")
				.build(port);

		RequestEntity<?> requestEntity = RequestEntity
				.post(uri)
				.contentType(MediaType.MULTIPART_FORM_DATA)
				.body(body);

		var responseEntity = restTemplate
				.exchange(requestEntity, Map.class);

		//--------------------------------------------------------------------------------
		//Assertion

		var entityBody = responseEntity.getBody();
		assertEquals("hoge", entityBody.get("name"));
		assertEquals("hoge.txt", entityBody.get("filename"));
		assertEquals("text/plain", entityBody.get("content-type"));
		assertEquals("Hello, world!", entityBody.get("body"));
	}
}
