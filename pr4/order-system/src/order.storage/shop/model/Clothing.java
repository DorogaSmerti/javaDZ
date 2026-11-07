package shop.model;

import lombok.Builder;

@Builder
public record Clothing(String name, String size) implements Product {}
