document.addEventListener('alpine:init', () => {
    Alpine.data('initData', (pageNo)=> ({
        pageNo: pageNo,
        products: {
            data: []
        },
        init() {
            this.loadProducts(this.pageNo);
        },
        loadProducts(pageNo) {
            $.getJSON("/api/products?page="+pageNo, (response)=> {
                this.products = response;
            })
        },
        addToCart(product) {
            addProductToCart(product);
        }
    }))
});