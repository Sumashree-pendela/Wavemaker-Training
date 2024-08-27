var todo = document.getElementById("inputField");
var priority = document.getElementById("todoPriority");
var addButton = document.getElementById("addTodo");
var todoContainer = document.getElementById("todosContainer");
var dialog = document.getElementById("dialog-content");
var dialogInput = document.getElementById("dialogInput");
var dialogPriority = document.getElementById("dialogPriority");
var dialogDate = document.getElementById("dialogDate");
var dialogTime = document.getElementById("dialogTime");
var dialogSave = document.getElementById("dialog-save");
var dialogCancel = document.getElementById("dialog-cancel");
var todoCounter = 0;
var theme = document.getElementById("theme");
var search = document.getElementById("todoSearch");
var todoTime = document.getElementById("todoTime");
var todoDate = document.getElementById("todoDate");
var todoSort = document.getElementById("todoSort");
var subtask = document.getElementById("subTaskDialogInput");
var subTaskDialog = document.getElementById("subTaskDialog");
var subTaskPriority = document.getElementById("subTaskDialogPriority");
var subTaskDate = document.getElementById("subTaskDialogDate");
var subTaskTime = document.getElementById("subTaskDialogTime");
var subTaskSave = document.getElementById("subTask-dialog-save");
var subTaskCancel = document.getElementById("subTask-dialog-cancel");
var logout = document.getElementById("logoutButton");
var subTaskCounter = 0;
var currentParentTodo;

loadTasks();

logoutButton.addEventListener('click', function() {
    // Perform the POST request to the logout endpoint
    fetch('http://localhost:8080/todo/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
    })
    .then(response => {
        if (response.ok) {
            // Redirect to login.html after successful logout
            window.location.href = 'login.html';
        } else {
            console.error('Logout failed:', response.statusText);
        }
    })
    .catch(error => {
        // Handle network errors or other issues
        console.error('Network error during logout:', error);
    });
});


addButton.addEventListener('click', addToDo);

subTaskSave.addEventListener('click', addSubTodo);

subTaskCancel.addEventListener('click', function () {
    subTaskDialog.style.display = 'none';
});


theme.addEventListener('click', themeChange);

search.addEventListener('keypress', function (e) {
    if (e.key === 'Enter') {
        searchTodos();
    }

});

    window.unload = function() {
        const urlParams = new URLSearchParams(window.location.search);
        console.log("url params..")
        console.log(urlParams);

        if (urlParams.has('error') && urlParams.get('error') === 'invalidUsername') {
            document.getElementById('errorMessage').style.display = 'block';
        }
    }

document.getElementById('export').addEventListener('click', exportTasks);

document.getElementById('importFileInput').addEventListener('change', importTasks);

todoSort.addEventListener('click', function () {
    console.log(todoSort.value);

    if (todoSort.value === 'Priority') {
        sortTodoByPriority();
    } else if (todoSort.value === 'Time') {
        sortTodoByTime();
    }
});

function requestNotificationPermission() {
    if (Notification.permission !== "granted") {
        Notification.requestPermission().then(permission => {
            console.log('Notification permission:', permission);
        });
    }
}


function addToDo() {
    let todoName = todo.value;
    let priorityValue = priority.value.toUpperCase();
    let timeValue = todoTime.value;
    let dateValue = todoDate.value;

    if (todoName.trim() === '' || priorityValue.trim() === '') {
        alert("To Do Name and Priority cannot be empty");
        return;
    }

    let now = new Date();
    let currentTime = now.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', hour12: false });
    let currentDate = now.toISOString().split('T')[0];

    if (dateValue < currentDate) {
        alert("You cannot add previous dates");
        return;
    }

    if (dateValue === currentDate && timeValue < currentTime) {
        alert("Add the time after " + currentTime);
        return;
    }

    // Ensure timeValue is in "HH:mm:ss" format
    if (timeValue.length <= 5) {
        // Add ":00" if timeValue doesn't include seconds
        timeValue = `${timeValue}:00`;
    }

    // Convert timeValue to 24-hour format if it's not already
    let timeParts = timeValue.split(':');
    let hours = parseInt(timeParts[0], 10);
    let minutes = timeParts[1];
    let seconds = timeParts[2] || '00'; // Default seconds to "00"

    if (hours < 10) {
        hours = `0${hours}`; // Ensure two-digit hour format
    }

    timeValue = `${hours}:${minutes}:${seconds}`;

    // Create a new todo object
    const newTodo = {
        name: todoName,
        priority: priorityValue,
        todoDate: dateValue,
        todoTime: timeValue
    };

    // Log the request value for debugging
    console.log("Request value....");
    console.log(JSON.stringify(newTodo));

    // Make a POST request to the backend API
    fetch('http://localhost:8080/todo/todo', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newTodo)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        console.log('Todo added:', data);
        loadTasks();
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
    });

    // Clear input fields
    todo.value = '';
    priority.value = '';
    todoTime.value = '';
    todoDate.value = '';
}


function createToDo(todoName, priorityValue, todoId, timeValue, dateValue, completed) {

    const todo = document.createElement('div');
    todo.classList.add('todo');
    todo.setAttribute('draggable', 'true');
    todo.setAttribute('id', todoId);


    let todoValue = document.createElement('input');
    todoValue.classList.add("todo-input");
    todoValue.type = "text";
    todoValue.value = todoName;
    todoValue.setAttribute('readOnly', 'readOnly');
    todo.appendChild(todoValue);

    const todoPriority = document.createElement('select');
    todoPriority.classList.add("todo-priority");
    todoPriority.innerHTML = `
        <option value="High">High</option>
        <option value="Medium">Medium</option>
        <option value="Low">Low</option>
    `;
    todoPriority.value = normalizePriority(priorityValue);
    todoPriority.setAttribute('readOnly', 'readOnly');
    todoPriority.disabled = true;
    todo.appendChild(todoPriority);

    let todoDate = document.createElement('input');
    todoDate.classList.add("todo-date");
    todoDate.type = "date";
    todoDate.value = dateValue;
    todo.appendChild(todoDate);

    let todoTime = document.createElement('input');
    todoTime.classList.add("todo-time");
    todoTime.type = "time";
    todoTime.value = timeValue;
    todoTime.setAttribute('readonly', 'readonly');
    todo.appendChild(todoTime);

     if (!completed) {
            let todoEdit = document.createElement('button');
            todoEdit.classList.add('btn', 'btn-success', 'todo-edit');
            todoEdit.type = "button";
            todoEdit.innerHTML = "Edit";
            todo.appendChild(todoEdit);

            let todoDelete = document.createElement('button');
            todoDelete.classList.add('btn', 'btn-danger', 'todo-del');
            todoDelete.type = "button";
            todoDelete.innerHTML = "Del";
            todo.appendChild(todoDelete);

            let todoSubtask = document.createElement('button');
            todoSubtask.classList.add('btn', 'btn-primary', 'todo-subtask');
            todoSubtask.type = "button";
            todoSubtask.innerHTML = "+";
            todo.appendChild(todoSubtask);

              let todoCompleted = document.createElement('button');
                   todoCompleted.classList.add('btn', 'btn-success', 'todo-completed');
                   todoCompleted.type = "button";
                   todoCompleted.innerHTML = "Done";
                   todo.appendChild(todoCompleted);

            todoEventListener(todo, todoEdit, todoDelete, todoSubtask, todoCompleted);
        }

    todoContainer.appendChild(todo);
//    sortTodoByPriority();

    return todo;

}

function normalizePriority(priority) {
    switch (priority.toUpperCase()) {
        case 'HIGH':
            return 'High';
        case 'MEDIUM':
            return 'Medium';
        case 'LOW':
            return 'Low';
        default:
            return 'Medium';
    }
}


function todoEventListener(todo, todoEdit, todoDelete, todoSubtask, todoCompleted) {
    const todoItem = todo.querySelector('.todo-input');
    const todoPriority = todo.querySelector('.todo-priority');
    const todoDate = todo.querySelector('.todo-date');
    const todoTime = todo.querySelector('.todo-time');

console.log("Inside todo event");
    todoCompleted.addEventListener('click', () => {
           const todoId = todo.getAttribute('id');

           // Make a POST request with the completed status in the URL path
           fetch(`http://localhost:8080/todo/todo?id=${todoId}`, {
               method: 'POST',
               headers: {
                   'Content-Type': 'application/json'
               }
           })
           .then(response => {
               if (!response.ok) {
                   console.error('Network response was not ok');
               }
               return response.text();
           })
           .then(() => {
               // Toggle the completed class in the UI
               todoItem.classList.toggle('completed');
              todoEdit.disabled = true;
                      todoDelete.disabled = true;
                      todoCompleted.disabled = true;

                      console.log('Updated Button States:', {
                                  edit: todoEdit.disabled,
                                  delete: todoDelete.disabled,
                                  complete: todoCompleted.disabled
                              });
               loadTasks(); // Optionally, reload the tasks if needed
           })
           .catch(error => {
               console.error('There was a problem with the completion operation:', error);
           });
       });

    todoEdit.addEventListener('click', () => {
        if (todoEdit.innerText === "Edit") {
            // Set global/current variables for editing
            currentTodo = todo; // Store the whole todo element for later use
            dialogInput.value = todoItem.value;
            dialogPriority.value = todoPriority.value;
            dialogDate.value = todoDate.value;
            dialogTime.value = todoTime.value;

            todoEdit.innerText = "Save";
            dialog.style.display = 'block';
        } else {
            todoEdit.innerText = "Edit";
        }
    });

    dialogSave.addEventListener('click', () => {
        if (dialogInput.value.trim() === '') {
            alert("To do name cannot be empty");
            return;
        }

        if (dialogPriority.value === '') {
            alert("Select priority for to do");
            return;
        }

        let now = new Date();
        let dateValue = dialogDate.value;
        let currentDate = now.toISOString().split('T')[0];

        if (dateValue < currentDate) {
            alert("You cannot add previous dates");
            return;
        }

        if (dialogTime.value === '') {
            alert("Time cannot be empty");
            return;
        }

        let timeValue = dialogTime.value;
        let currentTime = now.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', hour12: false });

        if (dateValue === currentDate && timeValue < currentTime) {
            alert("Add the time after " + currentTime);
            return;
        }

        const todoId = currentTodo.getAttribute('id'); // Use the stored todo element
        const updatedTodo = {
            name: dialogInput.value,
            priority: dialogPriority.value.toUpperCase(),
            todoDate: dialogDate.value,
            todoTime: dialogTime.value
        };

        fetch(`http://localhost:8080/todo/todo?id=${todoId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedTodo)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

        })
        .then(() => {
            dialog.style.display = 'none';
            todoEdit.innerText = 'Edit';
            loadTasks();
            location.reload();// Optionally, reload tasks if needed
        })
        .catch(error => {
            console.error('There was a problem with the update operation:', error);
        });
    });

    dialogCancel.addEventListener('click', () => {
        dialog.style.display = 'none';
        todoEdit.innerText = 'Edit';
    });


    todoDelete.addEventListener('click', () => {
        const todoId = todo.getAttribute('id');
        console.log(todoId);

        fetch(`http://localhost:8080/todo/todo?id=${todoId}`, {
            method: 'DELETE',
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => {
                    throw new Error(`Network response was not ok: ${response.statusText} - ${text}`);
                });
            }
            // Assuming no content is returned on successful deletion
            return response.text(); // Or you can proceed without returning anything if not needed
        })
        .then(() => {
            todoContainer.removeChild(todo);
            loadTasks(); // Reload tasks to reflect the deletion
        })
        .catch(error => {
            console.error('There was a problem with the delete operation:', error);
        });
    });




    todo.addEventListener('dragstart', (e) => {
        e.dataTransfer.setData('text/plain', todo.getAttribute('id'));
        e.dataTransfer.effectAllowed = 'move';
    });

    todo.addEventListener('dragover', (e) => {
        e.preventDefault();
        e.dataTransfer.dropEffect = 'move';
    });

    todo.addEventListener('drop', (e) => {
        e.preventDefault();
        const draggedElementId = e.dataTransfer.getData('text/plain');
        const draggedElement = document.getElementById(draggedElementId);

        if (draggedElement !== todo) {
            todoContainer.insertBefore(draggedElement, todo);
            saveTasks();
        }
    });

    todoSubtask.addEventListener('click', () => {
        currentParentTodo = todo;
        subTaskDialog.style.display = 'block';
    });
}

function fetchAndSaveTasks() {
    fetch('http://localhost:8080/todo/todo')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            // Assuming the data returned from the API is in the correct format
            const allTasks = data.map(task => {
                return {
                    id: task.id,
                    todoName: task.name,
                    todoPriority: task.priority,
                    todoTime: task.todoTime,
                    todoDate: task.todoDate,
                    subtasks: task.subtasks.map(subTask => ({
                        subTaskId: subTask.subTaskId,
                        subTaskName: subTask.subTaskName,
                        subTaskPriority: subTask.subTaskPriority,
                        subTaskTime: subTask.subTaskTime,
                        subTaskDate: subTask.subTaskDate
                    }))
                };
            });

            localStorage.setItem('allTodos', JSON.stringify(allTasks));
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}


function loadTasks() {
    const todoContainer = document.getElementById('todosContainer'); // Replace with the actual ID of your task container
    todoContainer.innerHTML = '';

    fetch('http://localhost:8080/todo/todo')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(allTasks => {
            console.log(allTasks);

            // Sort tasks: completed tasks go to the bottom
            allTasks.sort((a, b) => {
                           // Convert completed values to boolean if they are strings
                           const aCompleted = a.completed === true || a.completed === "true";
                           const bCompleted = b.completed === true || b.completed === "true";

                           // If both are equal (either both completed or not completed), return 0 to preserve the order
                           if (aCompleted === bCompleted) {
                               return 0;
                           }
                           // Sort so that completed tasks go to the bottom
                           return aCompleted ? 1 : -1;
                       });

            allTasks.forEach(task => {
                const todo = createToDo(task.name, task.priority, task.id, task.todoTime, task.todoDate, task.completed);

            });
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}


function themeChange() {
    console.log("Inside ")
    if (theme.innerText === 'Light') {
        document.body.classList.remove('dark-mode');
        document.body.classList.add('light-mode');
        theme.innerText = 'Dark';
    } else {
        document.body.classList.remove('light-mode');
        document.body.classList.add('dark-mode');
        theme.innerText = 'Light';
    }
}

function searchTodos() {
    console.log("Inside search method");

    var searchValue = search.value.toLowerCase();
    console.log(searchValue);
    const todos = document.querySelectorAll(".todo");

    todos.forEach(todo => {
        const todoName = todo.querySelector('.todo-input').value.toLowerCase();
        if (todoName.includes(searchValue)) {
            todo.style.display = 'block';
        } else {
            todo.style.display = 'none';
        }

//        const subTasks = todo.querySelectorAll('.todo-subtask');
//
//        if (subTasks !== '') {
//            subTasks.forEach(subtask => {
//                const subtaskName = subtask.querySelector('.sub-task-input').value.toLowerCase();
//                if (subtaskName.includes(searchValue)) {
//                    subtask.style.display = 'block';
//                } else {
//                    subtask.style.display = 'none';
//                }
//            });
//        }
    });
}


async function sortTodoByPriority() {
    try {
        // Fetch sorted todos from the backend API
        const response = await fetch('http://localhost:8080/todo/todoSort');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const todos = await response.json();

        todoContainer.innerHTML = '';

        // Add todos to the container
       allTasks.forEach(task => {
                       const todo = createToDo(task.name, task.priority, task.id, task.todoTime, task.todoDate, task.completed);
                   });

    } catch (error) {
        console.error('Error fetching or displaying todos:', error);
    }
}


function sortTodoByTime() {
    const todos = Array.from(document.querySelectorAll(".todo"));

    console.log(todos);
    todos.sort((todo1, todo2) => {
        let date1 = todo1.querySelector('.todo-date').value;
        let date2 = todo2.querySelector('.todo-date').value;
        let time1 = todo1.querySelector('.todo-time').value;
        let time2 = todo2.querySelector('.todo-time').value;

        console.log(date1 + date2);

        if (date1 === date2) {
            return time1.localeCompare(time2);
        }

        return date1.localeCompare(date2);
    });

    todoContainer.innerHTML = '';
    todos.forEach(todo => todoContainer.appendChild(todo));
}

function addSubTodo() {
    let subTaskName = subtask.value;

    if (subTaskName.trim() == '' || subTaskName == '') {
        alert("Sub Task Name cannot be empty");
        return;
    }

    let allTasks = JSON.parse(localStorage.getItem('allTodos')) || [];

    let duplicateSubTask = false;

    allTasks.forEach(task => {
        task.subtasks.forEach(subTask => {
            if (subTask.subTaskName.toLowerCase() === subTaskName.toLowerCase()) {
                duplicateSubTask = true;
            }
        });
    });

    if (duplicateSubTask) {
        alert("Sub Task already exists");
        return;
    }

    let subTaskPriorityValue = subTaskPriority.value;

    if (subTaskPriorityValue == '' || subTaskPriorityValue.trim() == '') {
        alert("Select priority for the sub task");
        return;
    }

    let now = new Date();

    let subTaskTimeValue = subTaskTime.value;
    let currentTime = now.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', hour12: false });

    let subTaskDateValue = subTaskDate.value;
    let currentDate = now.toISOString().split('T')[0];

    if (subTaskDateValue < currentDate) {
        alert("You cannot add previous dates");
        return;
    }

    if (subTaskDateValue === currentDate) {
        if (subTaskTimeValue < currentTime) {
            alert("Add the time after " + currentTime);
            return;
        }
    }

    createSubTask(subTaskName, subTaskPriorityValue, `subtask-${++subTaskCounter}`, subTaskTimeValue, subTaskDateValue, currentParentTodo);

    saveTasks();
    subTaskDialog.style.display = 'none';
    subtask.value = '';
    subTaskPriority.value = '';
    subTaskTime.value = '';
    subTaskDate.value = '';
}


function createSubTask(subTaskName, subTaskPriorityValue, subTaskId, subTaskTimeValue, subTaskDateValue, parentTodo) {
    const subTask = document.createElement('div');
    subTask.classList.add('sub-task');
    subTask.setAttribute('draggable', 'true');
    subTask.setAttribute('id', subTaskId);

    let subTaskValue = document.createElement('input');
    subTaskValue.classList.add("sub-task-input");
    subTaskValue.type = "text";
    subTaskValue.value = subTaskName;
    subTaskValue.setAttribute('readOnly', 'readOnly');
    subTask.appendChild(subTaskValue);

    const subTaskPriority = document.createElement('select');
    subTaskPriority.classList.add("sub-task-priority");
    subTaskPriority.innerHTML = `
        <option value="High">High</option>
        <option value="Medium">Medium</option>
        <option value="Low">Low</option>
    `;
    subTaskPriority.value = subTaskPriorityValue;
    subTaskPriority.setAttribute('readOnly', 'readOnly');
    subTaskPriority.disabled = true;
    subTask.appendChild(subTaskPriority);

    let subTaskDate = document.createElement('input');
    subTaskDate.classList.add("sub-task-date");
    subTaskDate.type = "date";
    subTaskDate.value = subTaskDateValue;
    subTask.appendChild(subTaskDate);

    let subTaskTime = document.createElement('input');
    subTaskTime.classList.add("sub-task-time");
    subTaskTime.type = "time";
    subTaskTime.value = subTaskTimeValue;
    subTaskTime.setAttribute('readonly', 'readonly');
    subTask.appendChild(subTaskTime);

    let subTaskEdit = document.createElement('button');
    subTaskEdit.classList.add('btn', 'btn-success', 'sub-task-edit');
    subTaskEdit.type = "button";
    subTaskEdit.innerHTML = "Edit";
    subTask.appendChild(subTaskEdit);

    let subTaskDelete = document.createElement('button');
    subTaskDelete.classList.add('btn', 'btn-danger', 'sub-task-del');
    subTaskDelete.type = "button";
    subTaskDelete.innerHTML = "Del";
    subTask.appendChild(subTaskDelete);

    subTaskEventListener(subTask, subTaskEdit, subTaskDelete);

    if (parentTodo) {
        parentTodo.appendChild(subTask);
    } else {
        todoContainer.appendChild(subTask);
    }

    return subTask;
}


function subTaskEventListener(subTask, subTaskEdit, subTaskDelete) {
    const subTaskItem = subTask.querySelector('.sub-task-input');
    const subTaskPriority = subTask.querySelector('.sub-task-priority');
    const subTaskDate = subTask.querySelector('.sub-task-date');
    const subTaskTime = subTask.querySelector('.sub-task-time');

    subTaskEdit.addEventListener('click', () => {
        if (subTaskEdit.innerText === "Edit") {
            subTaskEdit.innerText = "Save";

            subTaskItem.removeAttribute('readOnly');
            subTaskPriority.disabled = false;
            subTaskDate.removeAttribute('readOnly');
            subTaskTime.removeAttribute('readOnly');

        } else {
            subTaskItem.setAttribute('readOnly', 'readOnly');
            subTaskPriority.disabled = true;
            subTaskDate.setAttribute('readOnly', 'readOnly');
            subTaskTime.setAttribute('readOnly', 'readOnly');

            subTaskEdit.innerText = "Edit";
            saveTasks();
        }
    });

    subTaskDelete.addEventListener('click', () => {
        subTask.parentElement.removeChild(subTask);
        saveTasks();
    });

    subTask.addEventListener('dragstart', (e) => {
        e.dataTransfer.setData('text/plain', subTask.getAttribute('id'));
        e.dataTransfer.effectAllowed = 'move';
    });

    subTask.addEventListener('dragover', (e) => {
        e.preventDefault();
        e.dataTransfer.dropEffect = 'move';
    });

    subTask.addEventListener('drop', (e) => {
        e.preventDefault();
        const draggedElementId = e.dataTransfer.getData('text/plain');
        const draggedElement = document.getElementById(draggedElementId);

        if (draggedElement !== subTask) {
            todoContainer.insertBefore(draggedElement, subTask);
            saveTasks();
        }
    });
}

function exportTasks() {
    const allTasks = JSON.parse(localStorage.getItem('allTodos')) || [];
    const dataStr = JSON.stringify(allTasks, null, 2);
    const dataUri = 'data:application/json;charset=utf-8,' + encodeURIComponent(dataStr);

    const exportFileDefaultName = 'tasks.json';

    const linkElement = document.createElement('a');
    linkElement.setAttribute('href', dataUri);
    linkElement.setAttribute('download', exportFileDefaultName);
    linkElement.click();
}

function importTasks(event) {
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onload = function (e) {
        try {
            const importedTasks = JSON.parse(e.target.result);
            const existingTasks = JSON.parse(localStorage.getItem('allTodos')) || [];

            const mergedTasks = existingTasks.concat(importedTasks);
            localStorage.removeItem('allTodos');
            localStorage.setItem('allTodos', JSON.stringify(mergedTasks));

            loadTasks();
        } catch (err) {
            alert('Error reading the file. Make sure it is a valid JSON file.');
        }
    };

    reader.readAsText(file);
}

function scheduleNotification(itemId, dateValue, timeValue) {

    if (!("Notification" in window)) {
        alert("Doesnt support notification");
        return;
    }

    const taskDateTime = new Date(`${dateValue}T${timeValue}`);
    const notificationTime = taskDateTime.getTime() - (10 * 60 * 1000); // 10 minutes before notification
    console.log("Scheduled notification time: ", new Date(notificationTime));

    if (notificationTime > Date.now()) {
        const timeUntilNotification = notificationTime - Date.now();
        console.log("Time until notification: ", timeUntilNotification);

        setTimeout(() => {
            showNotification(itemId);
        }, timeUntilNotification);
    } else {
        console.log("Notification time is in the past, not scheduling.");
    }
}

function showNotification(itemId) {
    const item = document.getElementById(itemId);
    const text = item.querySelector('.text').value;

    if (Notification.permission === "granted") {
        new Notification('Task Reminder', {
            body: `Remainder: ${text} is due soon!`,
            icon: 'path/to/icon.png'
        });
    } else {
        console.log("Notification permission not granted.");
    }
}