const BOOK_STATE_KEY = "BOOKSTORE_STATE";

const getCart = function () {
    let cart = localStorage.getItem(BOOK_STATE_KEY);
    if (!cart) {
        cart = JSON.stringify({items: [], totalAmount: 0});
        localStorage.setItem(BOOK_STATE_KEY, cart);
    }
    return JSON.parse(cart);
}

const addProductToCart = function (product) {
    let cart = getCart();
    console.log("Cart: ", cart);
    let cartItem = cart.items.find(item => item.code === product.code);

    console.log("CartItem: ", cartItem);
    if (cartItem) {
        cartItem.quantity = parseInt(cartItem.quantity) + 1;
    } else {
        cart.items.push(Object.assign({}, product, {quantity: 1}));
    }
    console.log("CartItem: ", cartItem);

    localStorage.setItem(BOOK_STATE_KEY, JSON.stringify(cart));
    updateCartItemCount();
}

const updateProductQuantity = function (code, quantity)  {
    let cart = getCart();
    if (quantity < 1) {
        cart.items = cart.items.filter(item => item.code !== code);
    } else {
        let cartItem = cart.items.find(item => item.code === code);
        if (cartItem) {
            cartItem.quantity = parseInt(quantity);
        } else {
            console.log("Product code is not in the cart, ignoring...")
        }
    }
    localStorage.setItem(BOOK_STATE_KEY, JSON.stringify(cart));
    updateCartItemCount();
}

function updateCartItemCount(){
    let cart = getCart();
    let count = 0;

    cart.items.forEach(item => {
        count = count + item.quantity;
    });
    $('#cart-item-count').text('(' + count + ')')
}

const deleteCart = function (){
    localStorage.removeItem(BOOK_STATE_KEY);
    updateCartItemCount();
}

function getCartTotal(){
    let cart = getCart();
    let totalAmount = 0;
    cart.items.forEach(item => {
        totalAmount = totalAmount + (item.price * item.quantity);
    })
    return totalAmount;
}
