document.addEventListener('alpine:init', () => {
    Alpine.data('initData', orderNumber => ({
        orderNumber: orderNumber,
        orderDetails: {
            items: [],
            customer: {},
            deliveryAddress: {}
        },
        init() {
            updateCartItemCount();
            this.getOrdersDetails(this.orderNumber);
        },
        getOrdersDetails(orderNumber) {
            $.getJSON('http://localhost:8989/orders/api/orders/'+ orderNumber, data => {
                console.log("Get order response" ,data);
                this.orderDetails = data
            })
        }
    }))
});