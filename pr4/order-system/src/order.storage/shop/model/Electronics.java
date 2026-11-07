package shop.model;

import lombok.Builder;

@Builder
public record Electronics(String name, String brand) implements Product {}
