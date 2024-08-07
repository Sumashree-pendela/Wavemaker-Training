var add = document.getElementById('addToDo');
var input = document.getElementById('inputField');
var toDoContainer = document.getElementById('toDoContainer');
var search = document.getElementById('searchInput');
const dialog = document.getElementById('dialog');
const dialogInput = document.getElementById('dialog-input');
const dialogSave = document.getElementById('dialog-save');
const dialogCancel = document.getElementById('dialog-cancel');
var itemIdCounter = 0; // Counter to generate unique IDs for tasks

loadTasks();

add.addEventListener('click', addItem);
input.addEventListener('keypress', function(e) {
    if (e.key === "Enter") {
        addItem();
    }
});

search.addEventListener('keypress', function(e) {
        if(e.key === 'Enter') {
            searchTasks();
        }
});

function createItemElement(itemId, itemValue, itemPriority) {
    const item = document.createElement('div');
    item.classList.add('item');
    item.setAttribute('draggable', 'true');
    item.setAttribute('id', itemId);

    const item_content = document.createElement('div');
    item_content.classList.add('content');
    item.appendChild(item_content);

    const input_item = document.createElement('input');
    input_item.classList.add('text');
    input_item.type = 'text';
    input_item.value = itemValue;
    input_item.setAttribute('readonly', 'readonly');
    input_item.addEventListener('dblclick', function() {
        input_item.style.textDecoration = "line-through";
    });
    item_content.appendChild(input_item);

    const priority_input = document.createElement('input');
    priority_input.classList.add('priority');
    priority_input.type = 'text';
    priority_input.value = itemPriority;
    priority_input.placeholder = 'Priority';
    priority_input.style.marginLeft = '10px';
    item_content.appendChild(priority_input);

    const item_action = document.createElement('div');
    item_action.classList.add('actions');
    
    const edit_item = document.createElement('button');
    edit_item.classList.add('edit', 'btn', 'btn-success');
    edit_item.type = "dialog";
    edit_item.innerText = 'Edit';

    const delete_item = document.createElement('button');
    delete_item.classList.add('delete', 'btn', 'btn-danger', 'fa', 'fa-trash');
    delete_item.type = "button";
    delete_item.innerText = "Del";

    item_action.appendChild(edit_item);
    item_action.appendChild(delete_item);
    item.appendChild(item_action);

    addEventListenersToItem(item, edit_item, delete_item);

    return item;
}

function addEventListenersToItem(item, edit_item, delete_item) {
    const input_item = item.querySelector('.text');

    edit_item.addEventListener('click', () => {
        if (edit_item.innerText.toLowerCase() === "edit") {
            edit_item.innerText = "Save";
            dialogInput.value = input_item.value; // Set dialog input to current value
            dialog.style.display = 'block'; // Show the dialog box
            currentInputItem = input_item; // Store the current input item
            input_item.setAttribute("readonly", "readonly");
        }  else {
            edit_item.innerText = "Edit";
            input_item.setAttribute("readonly", "readonly");
            saveTasks(); 
        }
    });

    dialogSave.addEventListener('click', () => {
        const newValue = dialogInput.value;
        if (newValue !== '' && newValue.trim() !== '') {
            if (currentInputItem) {
                // Update the input item with the new value
                currentInputItem.value = newValue; 
            }
        }
        dialog.style.display = 'none'; 
        edit_item.innerText = "Edit";
        saveTasks(); 
    });
    
    dialogCancel.addEventListener('click', () => {
       // Hide the dialog box without saving changes
        dialog.style.display = 'none'; 
        edit_item.innerText = "Edit";
    });

    delete_item.addEventListener('click', () => {
        toDoContainer.removeChild(item);
        saveTasks(); // Save tasks to local storage after deletion
    });

    item.addEventListener('dragstart', (e) => {
        e.dataTransfer.setData('text/plain', item.getAttribute('id'));
        e.dataTransfer.effectAllowed = 'move';
    });

    item.addEventListener('dragover', (e) => {
        e.preventDefault(); 
        e.dataTransfer.dropEffect = 'move';
    });

    item.addEventListener('drop', (e) => {
        e.preventDefault();
        const draggedElementId = e.dataTransfer.getData('text/plain');
        const draggedElement = document.getElementById(draggedElementId);

        if (draggedElement && draggedElement !== item) {
            // Insert dragged element before the current item
            toDoContainer.insertBefore(draggedElement, item.nextSibling);
            saveTasks(); // Save tasks to local storage after reordering
        }
    });
}

function addItem() {
    const itemValue = input.value;
    const itemId = `item-${itemIdCounter++}`; 
    const item = createItemElement(itemId, itemValue, '');

    toDoContainer.appendChild(item);
    input.value = '';

    // Save tasks to local storage after adding
    saveTasks();
}

function saveTasks() {
    const items = document.querySelectorAll('.item');
    const tasks = Array.from(items).map(item => {
        const id = item.getAttribute('id');
        const text = item.querySelector('.text').value;
        const priority = item.querySelector('.priority').value;
        return { id, text, priority };
    });
    localStorage.setItem('todos', JSON.stringify(tasks));
}

function loadTasks() {
    const todos = JSON.parse(localStorage.getItem('todos')) || [];
    todos.forEach(todo => {
        const item = createItemElement(todo.id, todo.text, todo.priority);
        toDoContainer.appendChild(item);
    });
}

function searchTasks() {
    const query = searchInput.value.toLowerCase();
    const items = document.querySelectorAll('.item');
    
    items.forEach(item => {
        const text = item.querySelector('.text').value.toLowerCase();
        if (text.includes(query)) {
            item.style.display = 'flex'; 
        } else {
            item.style.display = 'none'; 
        }
    });
}

function themeChange() {
    document.body.classList.toggle('dark-theme');
    document.body.classList.toggle('light-theme');
}
