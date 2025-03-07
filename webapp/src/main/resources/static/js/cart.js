document.addEventListener('alpine:init', () => {
    Alpine.data('initData', () => ({
        cart: { items: [], totalAmount: 0},
        orderForm: {
            customer: {
                name: 'Felix',
                email: 'felix@gmail.com',
                phone: '254718424876'
            },
            deliveryAddress: {
                addressLine1: 'Nairobi',
                addressLine2: 'Rongai',
                city: 'Nairobi, Kenya',
                state: 'Syokimau',
                zipCode: '0123456789',
                country: 'Kenya'
            }
        },

        init(){
            updateCartItemCount();
            this.loadCart();
            this.cart.totalAmount = getCartTotal();
        },
        loadCart(){
            this.cart = getCart();
        },
        updateItemQuantity(code, quantity){
            updateProductQuantity(code, quantity);
            this.loadCart();
            this.cart.totalAmount = getCartTotal();
        },
        removeCart(){
            deleteCart();
        },
        createOrder(){
            let order = Object.assign({}, this.orderForm, {items: this.cart.items});

            $.ajax({
                url: '/api/orders',
                type: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(order),
                success: (response) => {
                    console.log("Response:", response);
                    this.removeCart();
                    // alert("Order successfully created!");
                    window.location = "/orders/"+response.orderNumber;
                },
                error: (error) => {
                    console.log("Order creation error: ", error);
                }
            })
        }
    }))
})