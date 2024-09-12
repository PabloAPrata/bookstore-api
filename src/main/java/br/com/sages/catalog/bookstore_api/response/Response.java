package br.com.sages.catalog.bookstore_api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Response wrapper for API responses")
public class Response<T> {

    @Schema(description = "Data payload")
    private T data;

    @Schema(description = "List of error messages")
    private List<String> errors;

    public List<String> getErrors() {
        if (this.errors == null) {
            this.errors = new ArrayList<String>();
        }
        return errors;
    }
}
