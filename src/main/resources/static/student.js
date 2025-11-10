let editingGrNo = null; // now using GR No

// Classes and divisions
const classes = [
    {className: 'Nursery', divisions: ['-']},
    {className: 'Jr. Kg', divisions: ['A', 'B']},
    {className: 'Sr. Kg', divisions: ['A', 'B']},
    {className: '1', divisions: ['A', 'B']},
    {className: '2', divisions: ['A', 'B']},
    {className: '3', divisions: ['A', 'B']},
    {className: '4', divisions: ['A', 'B']},
    {className: '5', divisions: ['A', 'B']},
    {className: '6', divisions: ['A', 'B']},
    {className: '7', divisions: ['A', 'B']},
    {className: '8', divisions: ['A', 'B']},
    {className: '9', divisions: ['A', 'B']},
    {className: '10', divisions: ['A', 'B']},
    {className: '11', divisions: ['Science', 'Commerce']},
    {className: '12', divisions: ['Science', 'Commerce']}
];

// Populate Class Dropdown
function populateClassDropdown() {
    const classSelect = document.getElementById('className');
    classSelect.innerHTML = '';
    classes.forEach(c => {
        let option = document.createElement('option');
        option.value = c.className;
        option.textContent = c.className;
        classSelect.appendChild(option);
    });
    populateDivisionDropdown(); // populate divisions for first class
}

// Populate Division Dropdown based on selected class
function populateDivisionDropdown() {
    const divisionSelect = document.getElementById('divisionOrStream');
    divisionSelect.innerHTML = '';
    const classSelected = document.getElementById('className').value;
    const cls = classes.find(c => c.className === classSelected);
    if (cls) {
        cls.divisions.forEach(d => {
            let option = document.createElement('option');
            option.value = d;
            option.textContent = d;
            divisionSelect.appendChild(option);
        });
    }
}

// Listen to class change
document.getElementById('className').addEventListener('change', populateDivisionDropdown);

// Show Add/Update form
function showForm(edit = false, student = null) {
    const formDiv = document.getElementById('studentForm');
    formDiv.style.display = 'block';
    document.getElementById('formTitle').innerText = edit ? 'Update Student' : 'Add Student';
    editingGrNo = edit ? student.grNo : null;

    if (edit && student) {
        for (const key in student) {
            const input = document.querySelector(`[name="${key}"]`);
            if (input) input.value = student[key];
        }
    } else {
        document.getElementById('form').reset();
        populateClassDropdown();
    }
}

// Cancel form
function cancelForm() {
    document.getElementById('studentForm').style.display = 'none';
    editingGrNo = null;
}

// Load all students
function loadStudents() {
    fetch('/students')
        .then(res => res.json())
        .then(data => {
            const tbody = document.querySelector('#studentTable tbody');
            tbody.innerHTML = '';
            data.forEach(s => {
                const tr = document.createElement('tr');

                tr.innerHTML = `
                    <td>${s.rollNo || ''}</td>
                    <td>${s.studentId || ''}</td>
                    <td>${s.grNo}</td>
                    <td>${s.fullName}</td>
                    <td>${s.className}</td>
                    <td>${s.divisionOrStream}</td>
                    <td>${s.dob}</td>
                    <td>${s.gender}</td>
                    <td>${s.caste}</td>
                    <td>${s.category}</td>
                    <td>${s.religion}</td>
                    <td>${s.bloodGrp}</td>
                    <td>${s.contactNo}</td>
                    <td>
                        <button class="edit-btn" data-student='${JSON.stringify(s)}'>Edit</button>
                        <button class="delete-btn" data-grno='${s.grNo}'>Delete</button>
                    </td>
                `;
                tbody.appendChild(tr);
            });

            // Attach Edit button listeners
            document.querySelectorAll('.edit-btn').forEach(btn => {
                btn.addEventListener('click', function () {
                    const student = JSON.parse(this.getAttribute('data-student'));
                    showForm(true, student);
                });
            });

            // Attach Delete button listeners
            document.querySelectorAll('.delete-btn').forEach(btn => {
                btn.addEventListener('click', function () {
                    const grNo = this.getAttribute('data-grno');
                    deleteStudent(grNo);
                });
            });
        });
}

// Add or Update student
document.getElementById('form').addEventListener('submit', function (e) {
    e.preventDefault();
    const data = {};
    new FormData(this).forEach((v, k) => data[k] = v);

    let url = '/students/add';
    let method = 'POST';
    if (editingGrNo) {
        url = `/students/update`;
        method = 'PUT';
    }

    fetch(url, {
        method: method,
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    })
        .then(res => res.text())
        .then(msg => {
            alert(msg);
            cancelForm();
            loadStudents();
        });
});

// Delete student
function deleteStudent(grNo) {
    if (confirm('Are you sure to delete?')) {
        fetch(`/students/delete/${grNo}`, {method: 'DELETE'})
            .then(res => res.text())
            .then(msg => {
                alert(msg);
                loadStudents();
            });
    }
}

// ----------------------
// Section header highlighting
// ----------------------
function showSection(id) {
    document.querySelectorAll('.section').forEach(s => {
        s.style.display = 'none';
        s.classList.remove('active');
    });
    const activeSection = document.getElementById(id);
    activeSection.style.display = 'block';
    activeSection.classList.add('active');

    document.querySelectorAll('.menu ul li').forEach(li => li.classList.remove('active'));
    document.querySelector(`.menu ul li[onclick="showSection('${id}')"]`).classList.add('active');
}

// Initial load
window.onload = () => {
    populateClassDropdown();
    loadStudents();

    // Show admin menu if logged-in user is admin
    const user = JSON.parse(sessionStorage.getItem('user'));
    if (user && user.isAdmin) {
        document.getElementById('adminSection').style.display = 'block';
    }
};
