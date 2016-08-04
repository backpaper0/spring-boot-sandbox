package sample;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface SampleRepository extends CrudRepository<Sample, String> {
    List<Sample> findByText(String text);
}
