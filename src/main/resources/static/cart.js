var numberOfItems = Number(document.getElementById("itemsInCart").textContent);
for (let i = 0; i < numberOfItems; i++) {
    var input = document.getElementById("amount"+i);
    input.addEventListener("change", updateValue);
}

function updateValue() {
    var totalPrice = 0;
    for (let i = 0; i < numberOfItems; i++) {
        var priceOfItem = parseFloat(document.getElementById("price"+i).textContent);
        var amountOfItems = document.getElementById("amount"+i).value;
        totalPrice += priceOfItem*amountOfItems;
    }
    document.getElementById("totalPrice").textContent="Total: " + totalPrice.toFixed(2) + "$";
}