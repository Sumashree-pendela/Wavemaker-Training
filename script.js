let memoryValue = "";

function clearDisplay() {
    document.getElementById("display").value = "";
}

function delLeftElement() {
    let display = document.getElementById("display").value;

    document.getElementById("display").value = display.slice(0, -1);
}

function add(a, b) {
    console.log("Adding two values: " + a + " " + b);
    document.getElementById("display").value = a + b;
}

function sub(a, b) {
    console.log("Subtraction of two values: " + a + " " + b);
    document.getElementById("display").value = a - b;
}

function mul(a, b) {
    console.log("Multiplication two values: " + a + " " + b);
    document.getElementById("display").value = a * b;
}

function div(a, b) {
    console.log("Division two values: " + a + " " + b);
    document.getElementById("display").value = a / b;
}

function rem(a, b) {
    console.log("Remainder of  two values: " + a + " " + b);
    document.getElementById("display").value = a % b;
}

function append(num) {
    display = document.getElementById("display").value;
    document.getElementById("display").value = display + num;
}

function storeMemory() {
    console.log("Storing value:" + Number(document.getElementById("display").value) + " in memory");
    memoryValue = Number(document.getElementById("display").value);
    clearDisplay();
}

function memoryRecall() {
    console.log("Recalling value in memory: " + memoryValue);
    document.getElementById("display").value = memoryValue;
}

function memoryClear() {
    console.log("Clearing memory value");
    memoryValue = "";
}

function memoryPlus(a) {
    document.getElementById("display").value = memoryValue + a;
}

function memoryMinus(a) {
    document.getElementById("display").value = memoryValue - a;
}

function squareRoot() {
    let displayValue = Number(document.getElementById("display").value);
    document.getElementById("display").value = Math.sqrt(displayValue);
}

function oneByX() {
    console.log("Reciprocal of the given value: " + document.getElementById("display").value);
    console.log(document.getElementById("display").value);
    let displayValue = document.getElementById("display").value;

    const fractionRegex = /^(-?[\d.]+)\/(-?[\d.]+)$/;
    const match = displayValue.match(fractionRegex);

    console.log(match);

    if (match) {
        const numerator = parseFloat(match[1]);
        const denominator = parseFloat(match[2]);
        document.getElementById("display").value = `${denominator}/${numerator}`;
    } else {
        const value = parseFloat(displayValue);
        if (value == 0) {
            alert("value cannot be zero");
        } else {
            document.getElementById("display").value = 1 / value;
        }
    }
}

function signChange() {
    console.log("Changing the sign");
    let displayValue = parseFloat(document.getElementById("display").value);

    if (displayValue == 0)
        document.getElementById("display").value = 0;
    else
        document.getElementById("display").value = -1 * displayValue;
}


function memoryOptions(value) {
    switch (value) {
        case 'mc':
            memoryClear();
            break;

        case 'mr':
            memoryRecall();
            break;

        case 'ms':
            storeMemory();
            break;

    }
}

function singleOperations(value) {

    switch (value) {
        case '1/x':
            oneByX();
            break;
            
        case '√':
            squareRoot();
            break;

        case '±':
            signChange();
            break;
    }
}

function cal() {
    let display = document.getElementById("display").value;
    console.log(display.substring(0, 2));

    if (display.substring(0, 2) == 'm+') {
        let displayValue = Number(display.substring(2).trim());
        memoryPlus(displayValue);
    }

    if (display.substring(0, 2) == 'm-') {
        let displayValue = Number(display.substring(2).trim());
        memoryMinus(displayValue);
    }

    const regex = /(-?[\d.]+)(\D)(-?[\d.]+)/;
    const match = display.match(regex);

    if (match) {
        const a = parseFloat(match[1]);
        const b = parseFloat(match[3]);

        let operator = match[2];

        switch (operator) {
            case "+":
                add(a, b);
                break;

            case "-":
                sub(a, b);
                break;

            case "*":
                mul(a, b);
                break;

            case "/":
                div(a, b);
                break;

            case "%":
                rem(a, b);
                break;

        }
    }
}