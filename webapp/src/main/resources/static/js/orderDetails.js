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
            $.getJSON(apiGatewayUrl+'/orders/api/orders/'+ orderNumber, data => {
                this.orderDetails = data
            })
        }
    }))
});