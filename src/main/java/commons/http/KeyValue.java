package commons.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author guorui1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyValue implements Serializable {
    private String key;
    private String value;
}
