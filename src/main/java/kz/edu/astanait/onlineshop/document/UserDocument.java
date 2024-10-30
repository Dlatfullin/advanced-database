package kz.edu.astanait.onlineshop.document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Document("users")
public class UserDocument {

    @Id
    private String id;
    private String email;
    private String password;
    private String fullName;
    private List<TokenDocument> tokens = new ArrayList<>();

    public void addToken(final TokenDocument token) {
        if (token != null) {
            tokens.add(token);
        }
    }
}
