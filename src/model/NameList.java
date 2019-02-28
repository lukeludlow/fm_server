package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * simple model to match json for given names during fill service
 */
public class NameList {
    private String[] data;
}
