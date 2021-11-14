// To get elements
// document.elem
// Available elems include all, head, body, forms, links, images, etc etc
// We can access these by array index.

console.log(document.images[2]);

// To get an element by ID or class we can do...

document.getElementById('element-id-here');
document.getElementsByClassName('element-class-here');

// Note that it is "Elements" plural. This is because there are multiple.

// When we save an element to a variable, changing that variable will change the element.
// This works almost like call by reference in c++.

let example = getElementById('my-element');
example.style.color = '#000000'

// We can also do this with classes. They work as an array and as such, to change all, we must loop through them in an array.
let exampleClass = getElementsByClassName('my-examples');
for (let i = 0; i < exampleClass.length; ++i) {
    exampleClass[i].style.color = '#000000';
}

// We can also use what's called a query selector. This allows us to search for a specific query
let queryExample = document.querySelector('selector');

//querySelectorAll lets us select all items of a certain class/selector. This lets us use array functions on the items.
let queryExampleList = document.querySelectorAll('.item');
for (let i = 0; i < queryExampleList.length; ++i) {
    console.log(queryExampleList[i]);
}

// This lets us pick things exactly the same way we do in CSS with CSS selectors. Very useful.
// We can edit the text or html directly by editting these selectors.
console.log(example.textContent); // Changes the text content of the text inside an html tag.
console.log(example.innerText);   // This is pretty much the same as textContent except it pays attention to styles.
console.log(example.innerHTML);   // Allows us to add and change the HTML inside our element.

// Traversing the DOM!
// This lets us look at parent and child elements.
console.log(document.querySelector('h1').childNodes); // Note this will show the line breaks and text nodes as well! This isn't fantastic!
console.log(document.querySelector('h1').firstChild);
console.log(document.querySelector('h1').lastChild);

console.log(document.querySelector('h1').children);   // This is the better alternative! Use these instead to make life easier. c:
console.log(document.querySelector('h1').firstElementChild);
console.log(document.querySelector('h1').lastElementChild);
console.log(document.querySelector('h1').parentNode);

// We can acess siblings as well! Note, the same allies as did with children.
console.log(document.querySelector('h1').nextSibling); // Note this will show the line breaks and text nodes as well! This isn't fantastic!
console.log(document.querySelector('h1').previousSibling);

console.log(document.querySelector('h1').nextElementSibling); // Note this will show the line breaks and text nodes as well! This isn't fantastic!
console.log(document.querySelector('h1').previousElementSibling);

// We can also create elements in our HTML code.
let newElem = document.createElement('div');

// We can then add attributes and content and children to this element.
newElem.setAttribute('title', 'hello div');
newElem.id = 'id';
newElem.class = "your classes here"

// To create new text we can use functions.
let myText = document.createTextNode('Hello, world!');

// We can then add it to our div!
newElem.appendChild(myText);

// Now we can put it into our HTML!
container.insertBefore(newElem, example);

// Events! :D
// OnClick events are outdated! Don't use them. Instead we use event listeners and queryselectors.
let event = document.getElementById('button').addEventListener('click', buttonClick);

function buttonClick(){
    console.log("Your buttons have been pressed teehee. c:");
}

// So here what happens is we have a variable that is set to an event listener that monitors an element. When 'click' is detected, it will call the function 'buttonClick';
// selector.addEventListener('action', function);
// You can pass in an event as an argument in the function with the key argument 'e';
function eventTracker(e){
    console.log(e.type);
}
