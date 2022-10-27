package com.mediscreen.risk.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();


    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 404) {

            return new ResourceNotFoundException("Patient non trouvé");
        }

        return defaultErrorDecoder.decode(s, response);
    }
}
