package com.example.demouploadcsv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;

/**
 * The type Response message.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO implements Serializable {
    private String message;
}
