package commons.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author guorui1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateResult {
    List<String> messageList;

    public boolean isEmpty() {
        return messageList == null || messageList.isEmpty();
    }
}
