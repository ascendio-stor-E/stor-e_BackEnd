package com.ascendio.store_backend.dto;

public record DalleImageGenerationResponse(DalleImageGenerationResponseUrl[] data) {

    public DalleImageGenerationResponse(DalleImageGenerationResponseUrl[] data) {
        this.data = data;
    }

    @Override
    public DalleImageGenerationResponseUrl[] data() {
        return data;
    }
}
