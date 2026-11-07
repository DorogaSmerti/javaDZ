package shop.model;

import java.util.UUID;

public record Order<T extends Product>(UUID id, T product) {}
