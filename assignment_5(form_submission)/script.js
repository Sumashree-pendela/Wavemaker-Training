document.addEventListener('DOMContentLoaded', function () {

    fetch('https://randomuser.me/api/').then(
        res => res.json()).then(
            data => {
                const iData = data.results[0];

                console.log(iData);

                document.getElementById('fname').value = iData.name.first;
                document.getElementById('lname').value = iData.name.last;
                document.getElementById('email').value = iData.email;
                document.querySelector('input[name="age"]').value = iData.dob.age;

                document.getElementById('password').value = iData.login.password;
                document.getElementById('cpassword').value = iData.login.password;
                document.getElementById('sincome').value = 'Employed';
                document.getElementById('income').value = 10000;
                document.getElementById('bio').value = 'Bio';

                if (iData.gender === 'male') {
                    document.getElementById('male').checked = true;
                } else if (iData.gender === 'female') {
                    document.getElementById('female').checked = true;
                }

                //check any of the hobbies as there is no value
                document.getElementById('music').checked = true;

                document.getElementById('file').src = iData.picture.large;

            });
}
);


function postData(event) {
    event.preventDefault();
    const form = document.querySelector('form');

    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());
    console.log(data);

    if (data.password !== data.cpassword) {
        alert('Passwords do not match.');
        return;
    }

    fetch('https://httpbin.org/post', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data),
    })
    .then(res => res.json())
    .then(responseData => {
        alert("Data uploaded successfully");
    })
    .catch(error => {
        console.error("Error while uploading data", error);
    });
}

document.getElementById('income').addEventListener('input', function() {
    document.getElementById('incomeValue').textContent = `${this.value / 1000}k`;
});

document.querySelector('form').addEventListener('submit', postData);

