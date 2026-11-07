module order.processing {
    requires transitive order.storage;
    exports shop.processing to order.workers;
}
