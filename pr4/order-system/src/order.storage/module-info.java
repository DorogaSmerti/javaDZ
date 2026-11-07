module order.storage {
    requires static lombok;
    exports shop.model;
    exports shop.storage to order.processing, order.workers;
}
