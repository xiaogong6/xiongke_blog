package com.api.dto.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author admin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResultDTO<T> {

    private List<T> records;

    private Integer count;

}
