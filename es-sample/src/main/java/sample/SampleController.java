package sample;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    private final SampleRepository repository;

    public SampleController(SampleRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Iterable<Sample> getAll() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Sample get(@PathVariable String id) {
        return repository.findOne(id);
    }

    @GetMapping("find")
    public List<Sample> find(@RequestParam String text) {
        return repository.findByText(text);
    }

    @PostMapping
    public Sample post(@RequestParam String text) {
        Sample entity = new Sample();
        entity.setText(text);
        return repository.save(entity);
    }
}
