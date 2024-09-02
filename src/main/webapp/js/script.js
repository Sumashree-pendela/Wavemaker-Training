const leaveContainer = document.getElementById('leaveContainer');
const teamLeaveContainer = document.getElementById('teamLeaveContainer');
const logoutButton = document.getElementById('logoutBtn');
const teamLeavesTab = document.getElementById('teamLeaveRequestsTab');
const personalLeavesTab = document.getElementById('leaveRequestsTab');
const addLeaveBtn = document.getElementById('addLeaveBtn');
const teamHeader = document.getElementById('team-leave-header');
const cancelButton = document.getElementById('cancelButton');
const profilePic = document.getElementById('profilePic');
const userName = document.getElementById('userName');
var gender = "";
const profileContainer = document.getElementById('profileContainer');
const employeeName = document.getElementById('employeeName');
const employeeDOB = document.getElementById('employeeDOB');
const employeeGender = document.getElementById('employeeGender');
const employeePhone = document.getElementById('employeePhone');
const closeDialogButton = document.getElementById('closeDialog');
const dialog = document.getElementById('employeeDialog');
const trackerName = document.getElementById('trackerName');
const trackerUsed = document.getElementById('trackerUsed');
const trackerLeft = document.getElementById('trackerUsed');
const trackerTotal = document.getElementById('trackerUsed');
const trackerDialog = document.getElementById('leaveTrackerDialog');
const trackerContent = document.getElementById('leaveTrackerContent');
const teamTrackerContent = document.getElementById('teamLeaveTrackerContent');

const leaveNameMapping = {
    'SICK_LEAVE': 'Sick',
    'CASUAL': 'Casual',
    'MATERNITY': 'Maternity',
    'PAID_LEAVE': 'Paid',
    'PATERNITY': 'Paternity'
};

const leaveStatusMapping = {
    'PENDING': 'Pending',
    'REJECTED': 'Rejected',
    'APPROVED': 'Approved'
};

logoutButton.style.display = 'none';
userName.style.display = 'none';

document.addEventListener('DOMContentLoaded', () => {
leaveRequestsTab.classList.add('active');
teamHeader.style.display = 'none';
    saveLeaves();
    fetchUserProfile();
});

    // Show username and logout button when profile picture is clicked
    profilePic.addEventListener('click', function() {
   if (userName.style.display === 'none' || userName.style.display === '') {
               fetchUserProfile(); // Fetch the user profile name
               userName.style.display = 'block';
               logoutButton.style.display = 'block';
           } else {
               userName.style.display = 'none';
               logoutButton.style.display = 'none';
           }
    });

teamLeavesTab.addEventListener('click', function() {
    console.log("inside team tab...");

     leaveRequestsTab.classList.remove('active');
     teamLeaveRequestsTab.classList.add('active');
    teamHeader.style.display = 'flex';
    teamLeaveContainer.style.display = 'block';
    leaveContainer.style.display = 'none';
    addLeaveBtn.style.display = 'none';
    trackerContent.style.display = 'none';

    saveTeamLeaves();
});

personalLeavesTab.addEventListener('click', function() {
    // Show personal leave container and hide team leave container
    leaveRequestsTab.classList.add('active');
    teamLeaveRequestsTab.classList.remove('active');
    leaveContainer.style.display = 'block';
    teamLeaveContainer.style.display = 'none';
    addLeaveBtn.style.display = 'block';
    teamHeader.style.display = 'none';
    trackerContent.style.display = 'flex';
});

document.getElementById('addLeaveBtn').addEventListener('click', function() {

   fetchUserProfile();
console.log("Inside add leave btn function......");
console.log("gender.." + gender);

const leaveNameSelect = document.getElementById('leaveName');
    const maternityOption = leaveNameSelect.querySelector('option[value="MATERNITY"]');
    const paternityOption = leaveNameSelect.querySelector('option[value="PATERNITY"]');

    if (gender === 'MALE') {
    console.log("Inside male if");
        maternityOption.style.display = 'none';
        paternityOption.style.display = 'block';
    } else if (gender === 'FEMALE') {
    console.log("Inside female if");
        paternityOption.style.display = 'none';
        maternityOption.style.display = 'block';
    }


 const dateFromInput = document.getElementById('dateFrom');
     const dateToInput = document.getElementById('dateTo');

     // Get today date
     const today = new Date().toISOString().split('T')[0];

     // Setting min attribute
     dateFromInput.setAttribute('min', today);
     dateToInput.setAttribute('min', today);

     // changing dateTo min attribute..
     dateFromInput.addEventListener('change', function() {
         dateToInput.setAttribute('min', dateFromInput.value);
     });

    document.getElementById('leaveForm').style.display = 'block';

});

      function fetchUserProfile() {
      console.log("Inside fetch profile..");
            fetch('http://localhost:8080/leave_management//leave/employee?profile=1')
                .then(response => response.json())
                .then(data => {
                console.log(data.gender);
                    userName.textContent = data.name;
                    gender = data.gender;
                })
                .catch(error => {
                    console.error('Error fetching profile data:', error);
                });
        }

cancelButton.addEventListener('click', function() {
document.getElementById('leaveRequestForm').reset();
document.getElementById('leaveForm').style.display = 'none';
})

logoutButton.addEventListener('click', function() {
    console.log("inside logout btn");
    fetch('http://localhost:8080/leave_management/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (response.ok) {
            // Redirecting to login.html
            window.location.href = 'login.html';
        } else {
            console.error('Logout failed:', response.statusText);
        }
    })
    .catch(error => {
        console.error('Network error during logout:', error);
    });
});

function saveLeaves() {
    fetch('http://localhost:8080/leave_management/leave/leave_management', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        leaveContainer.querySelectorAll('.leave-item').forEach(item => item.remove());

        // Iterate over each leave request and create leave items
        data.forEach(leave => {
            createLeaveRequest(leave.dateFrom, leave.dateTo, leave.leaveName, leave.reason, leave.status, leave.appliedDate);
        });
    })
    .catch(error => {
        console.error('Error fetching leave requests:', error);
    });

   //leaves tracker..
    leaveTracker();

}

function leaveTracker() {
    console.log("inside leave tracker...");
    const apiUrl = 'http://localhost:8080/leave_management/leave/leaveTracker';
    fetch(apiUrl, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
        // Get the dialog container
        const dialog = document.getElementById('leaveTrackerContent');
        dialog.innerHTML = '';

        // Creating heading
        const headingDiv = document.createElement('div');
        headingDiv.classList.add('heading');
        headingDiv.innerHTML = `
            <p><strong>Name</strong></p>
            <p><strong>Used</strong></p>
            <p><strong>Left</strong></p>
            <p><strong>Total</strong></p>
        `;
        dialog.appendChild(headingDiv);

        // Creating rows..
        data.forEach(leaveTracker => {
            const rowDiv = document.createElement('div');
            rowDiv.classList.add('row');
               const newLeaveName = leaveNameMapping[leaveTracker.leaveName] || leaveTracker.leaveName;
            rowDiv.innerHTML = `
                <p><strong>${newLeaveName}</strong></p>
                <p>${leaveTracker.usedLeaves}</p>
                <p>${leaveTracker.leavesLeft}</p>
                <p>${leaveTracker.totalLeaves}</p>
            `;
            dialog.appendChild(rowDiv);
        });
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
    });
}


function saveTeamLeaves() {
    console.log("Inside Team leaves...");
    fetch('http://localhost:8080/leave_management/leave/employeeLeaves', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        console.log("Data of employee leaves...");
        console.log(data);

        teamLeaveContainer.querySelectorAll('.team-leave-item').forEach(item => item.remove());
        data.forEach(leave => {
        console.log("Leave obj");
        console.log(leave);
            createTeamLeaveRequest(leave.id, leave.dateFrom, leave.dateTo, leave.leaveName, leave.reason, leave.status, leave.employeeName, leave.appliedBy, leave.appliedDate);
        });
    })
    .catch(error => {
        console.error('Error fetching team leave requests:', error);
    });
}

document.getElementById('leaveRequestForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const dateFrom = document.getElementById('dateFrom').value;
    const dateTo = document.getElementById('dateTo').value;
    const leaveName = document.getElementById('leaveName').value;
    const reason = document.getElementById('reason').value;

    if(dateTo < dateFrom) {
    alert("Correct the selected dateFrom and dateTo");
    }

    const requestData = {
        dateFrom: dateFrom,
        dateTo: dateTo,
        leaveName: leaveName,
        reason: reason
    };

    console.log("Request data:");
    console.log(requestData);
    fetch('http://localhost:8080/leave_management/leave/leave_management', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(errorData => {
                throw new Error(errorData.error || 'Something went wrong');
            });
        }
        return response.json();
    })
    .then(data => {
        console.log('Leave request submitted:', data);
        createLeaveRequest(data.dateFrom, data.dateTo, data.leaveName, data.reason, data.status, data.appliedDate);
        document.getElementById('leaveRequestForm').reset();
        document.getElementById('leaveForm').style.display = 'none';
    })
    .catch(error => {
        console.error('Error occurred:', error);
        alert("Error: " + error.message);
    });
});

function createLeaveRequest(dateFrom, dateTo, leaveName, reason, status, appliedDate) {

const newLeaveName = leaveNameMapping[leaveName] || leaveName;
const newStatus = leaveStatusMapping[status] || status;
    const leaveItem = document.createElement('div');
    leaveItem.className = 'row mb-3 leave-item';

    leaveItem.innerHTML = `
        <div class="col-md-2">${appliedDate}</div>
        <div class="col-md-2">${newLeaveName}</div>
        <div class="col-md-2">${dateFrom}</div>
        <div class="col-md-2">${dateTo}</div>
        <div class="col-md-2">${reason}</div>
        <div class="col-md-2">${newStatus}</div>
    `;

    leaveContainer.appendChild(leaveItem);
    leaveTracker();
}

function createTeamLeaveRequest(leaveId, dateFrom, dateTo, leaveName, reason, status, employee, appliedBy, appliedDate) {
    console.log("Inside team leave request...");
    console.log(employee, dateFrom, dateTo, leaveName, reason, status);

const newLeaveName = leaveNameMapping[leaveName] || leaveName;
const newStatus = leaveStatusMapping[status] || status;

    let teamLeaveItem = document.createElement('div');
    teamLeaveItem.classList.add('team-leave-item');
    teamLeaveItem.setAttribute('id', leaveId);

    let dateDiv = document.createElement('div');
        dateDiv.classList.add('team-date');
        dateDiv.textContent = appliedDate;
        teamLeaveItem.appendChild(dateDiv);

    let employeeDiv = document.createElement('div');
    employeeDiv.classList.add('team-employee');
    employeeDiv.textContent = employee;
    teamLeaveItem.appendChild(employeeDiv);

    let leaveTypeDiv = document.createElement('div');
    leaveTypeDiv.classList.add('team-leaveType');
    leaveTypeDiv.textContent = newLeaveName;
    teamLeaveItem.appendChild(leaveTypeDiv);

    let dateFromDiv = document.createElement('div');
    dateFromDiv.classList.add('team-dateFrom');
    dateFromDiv.textContent = dateFrom;
    teamLeaveItem.appendChild(dateFromDiv);

    let dateToDiv = document.createElement('div');
    dateToDiv.classList.add('team-dateTo');
    dateToDiv.textContent = dateTo;
    teamLeaveItem.appendChild(dateToDiv);

    let reasonDiv = document.createElement('div');
    reasonDiv.classList.add('team-reason');
    reasonDiv.textContent = reason;
    teamLeaveItem.appendChild(reasonDiv);

    let statusDiv = document.createElement('div');
    statusDiv.classList.add('team-status');
    statusDiv.textContent = newStatus;
    teamLeaveItem.appendChild(statusDiv);

    let approveButton, rejectButton;
    let buttonContainer = document.createElement('div');

    let detailsButton = document.createElement('button');
    detailsButton.classList.add('btn', 'btn-secondary', 'team-details');
    detailsButton.textContent = 'Details';
    buttonContainer.appendChild(detailsButton);

if (status === 'PENDING') {
        // Creating the button container div
        buttonContainer.classList.add('team-button');

        // Create and append the approve button
        approveButton = document.createElement('button');
        approveButton.classList.add('btn', 'btn-success', 'team-approve');
        approveButton.textContent = 'Approve';
        buttonContainer.appendChild(approveButton);

        // Create and append the reject button
        rejectButton = document.createElement('button');
        rejectButton.classList.add('btn', 'btn-danger', 'team-reject');
        rejectButton.textContent = 'Reject';
        buttonContainer.appendChild(rejectButton);

    } else {
        // hing when status not pending
        teamLeaveItem.classList.add('no-buttons');
    }

   teamLeaveItem.appendChild(buttonContainer);
    teamLeaveContainer.appendChild(teamLeaveItem);
    detailsButtonEventListener(detailsButton, appliedBy);

    if (approveButton && rejectButton) {
        teamLeaveEventListener(approveButton, rejectButton, teamLeaveItem, appliedBy);
    }
}

function detailsButtonEventListener(detailsButton, appliedBy) {
detailsButton.addEventListener('click', () => {
    detailsEventListener(detailsButton, appliedBy);
});
}

function detailsEventListener(detailsButton, employeeId) {
        const apiUrl = `http://localhost:8080/leave_management/leave/employee?employeeId=${employeeId}`;

        fetch(apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log("Details of employee:", data);
                employeeName.textContent = data.name;
                employeeDOB.textContent = data.dob;
                employeeGender.textContent = data.gender;
                employeePhone.textContent = data.phoneNumber
                dialog.style.display = 'block';
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
 }

 closeDialogButton.addEventListener('click', function() {
 dialog.style.display = 'none';
 });


function teamLeaveEventListener(approveButton, rejectButton, teamLeaveItem, appliedBy) {
    approveButton.addEventListener('click', function() {
        let leaveId = teamLeaveItem.getAttribute('id');
        sendEmployeeIdForLeaveTracker(appliedBy, 'Approve', leaveId, teamLeaveItem);
    });

    rejectButton.addEventListener('click', function() {
        let leaveId = teamLeaveItem.getAttribute('id');
         sendEmployeeIdForLeaveTracker(appliedBy, 'Reject', leaveId, teamLeaveItem);
    });
}


function sendEmployeeIdForLeaveTracker(appliedBy, buttonName, leaveId, teamLeaveItem) {

 console.log("inside team leave tracker...");
    const apiUrl = `http://localhost:8080/leave_management/leave/leaveTracker?appliedBy=${appliedBy}`;
    fetch(apiUrl, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        const dialog = document.getElementById('teamLeaveTrackerContent');
        dialog.style.display = 'flex';
        dialog.style.flexDirection = 'column';
        dialog.innerHTML = '';

        // Creating  heading
        const headingDiv = document.createElement('div');
        headingDiv.style.display = 'flex';
        headingDiv.style.justifyContent = 'space-between';
        headingDiv.classList.add('heading');
        headingDiv.innerHTML = `
            <p><strong>Name</strong></p>
            <p><strong>Used</strong></p>
            <p><strong>Left</strong></p>
            <p><strong>Total</strong></p>
        `;
        dialog.appendChild(headingDiv);

        // Creating data rows
        data.forEach(teamLeaveTracker => {
            const rowDiv = document.createElement('div');
            rowDiv.style.display = 'flex';

            rowDiv.classList.add('column');
               const newLeaveName = leaveNameMapping[teamLeaveTracker.leaveName] || teamLeaveTracker.leaveName;
            rowDiv.innerHTML = `
                <p><strong>${newLeaveName}</strong></p>
                <p>${teamLeaveTracker.usedLeaves}</p>
                <p>${teamLeaveTracker.leavesLeft}</p>
                <p>${teamLeaveTracker.totalLeaves}</p>
            `;
            dialog.appendChild(rowDiv);
        });

         const buttonContainer = document.createElement('div');
                buttonContainer.classList.add('button-container');
                 buttonContainer.style.marginTop = 'auto';
                         buttonContainer.style.display = 'flex';
                         buttonContainer.style.justifyContent = 'flex-end';

                // Create the first button with the provided buttonName
                const actionButton = document.createElement('button');
                actionButton.textContent = buttonName;
                actionButton.classList.add('btn', 'btn-primary', 'teamApproveButton');

                // Optionally, add an event listener to the action button
                actionButton.addEventListener('click', () => {
                 if (buttonName === 'Approve') {
                 console.log("Button Name.." + buttonName);
                 dialog.style.display= 'none';
                 //1 for approve
                 sendLeaveAction(leaveId, 1, teamLeaveItem);
                 } else {
                 dialog.style.display= 'none';
                 //0 for reject
                 sendLeaveAction(leaveId, 0, teamLeaveItem);
                 }

                });

                // Creating the close button
                const closeButton = document.createElement('button');
                closeButton.textContent = 'Close';
                closeButton.classList.add('btn', 'btn-secondary', 'teamCloseButton'); // Bootstrap classes for styling

                closeButton.addEventListener('click', () => {
                dialog.style.display= 'none';
                    dialog.innerHTML = '';
                });

                buttonContainer.appendChild(actionButton);
                buttonContainer.appendChild(closeButton);

                dialog.appendChild(buttonContainer);
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
    });
}

function sendLeaveAction(leaveId, approve, teamLeaveItem) {
    let url = `http://localhost:8080/leave_management/leave/leave_management?leaveId=${leaveId}&approve=${approve}`;
    fetch(url, {
        method: 'PUT'
    })
    .then(response => {
        if (response.ok) {
            console.log(`Leave action sent successfully for leaveId: ${leaveId} with approve status: ${approve}`);
            if (approve === 1) {
                            teamLeaveItem.classList.add('approved');
                            teamLeaveItem.querySelector('.status').textContent = 'Approved';
                        } else {
                            teamLeaveItem.classList.add('rejected');
                            teamLeaveItem.querySelector('.status').textContent = 'Rejected';
                        }
        } else {
            console.error('Failed to send leave action:', response.statusText);
        }
    })
    .catch(error => {
        console.error('Error in sending leave action:', error);
    });
    saveTeamLeaves();
}

