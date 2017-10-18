<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="header.jspf" %>
</head>

</body>

<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Управление задачами студентов</a>
        </div>
    </div>
</nav>

<div class="container">
    <div id="selectForm" class="row">
    </div>
    <div class="row">
        <form class="form-horizontal" id="add-student-form">
            <div class="form-group">
                <span class="col-md-2">
                    <input type=text class="form-control" placeholder="Имя" id="firstName">
                </span>
                <span class="col-md-2">
                    <input type="text" class="form-control" placeholder="Фамилия" id="lastName">
                </span>
                <span class="col-md-2">
                    <button type="submit" id="add-student-btn" class="btn btn-primary btn-md">
                        Добавить студента
                    </button>
                </span>
                <span id="add-student-error" style="color: red;"></span>
            </div>
        </form>
    </div>
    <div class="row">
        <h2>Список студентов и их задач</h2>
        <span id="get-students-error" style="color: red;"></span>
        <div id="studentTaskViewsId"/>
    </div>
</div>

<%@ include file="footer.jspf" %>

<script>
    $(function () {
        $("#add-student-form").submit(function (event) {
            $("#add-student-btn").prop("disabled", false);
            event.preventDefault();
            addViaAjax();
        });

        getStudentTaskViews(${studentTaskViews});
    });

    function getStudentTaskViews() {
        console.debug("getStudentTaskViews");
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/student-task-manager/studentTaskViews",
            data: JSON.stringify({}),
            dataType: 'json',
            timeout: 100000,
            success: function (data) {
                console.log("studentTaskViews SUCCESS: ", data);
                if (data.code == 200) {
                    displayStudentTaskViews(data.studentTaskViews);
                    displaySelects(data.studentTaskViews, data.tasks);
                    $("#get-students-error").text("");
                } else {
                    $("#get-students-error").text(data.errorMessage);
                }
            },
            error: function (errorData) {
                console.warn("studentTaskViews ERROR: ", errorData);
                $("#get-students-error").text(data.errorMessage);
            },
            done: function (e) {
                console.log("studentTaskViews DONE");
            }
        });
    }

    function updateViaAjax() {
        console.debug("updateViaAjax");
        var studentTask = {
            "studentId": parseInt($('#studentId').val()),
            "taskId": parseInt($('#taskId').val()),
        };
        console.debug(studentTask);

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/student-task-manager/studentTask/update",
            data: JSON.stringify(studentTask),
            dataType: 'json',
            timeout: 100000,
            success: function (data) {
                console.log("updateViaAjax SUCCESS: ", data);
                if (data.code == 200) {
                    getStudentTaskViews();
                    $("#update-error").text("");
                } else {
                    $("#update-error").text(data.errorMessage);
                }
                $("#add-student-error").text("");
                $("#get-students-error").text("");
            },
            error: function (errorData) {
                console.warn("updateViaAjax ERROR: ", errorData);
                $("#update-error").text(data.errorMessage);
            },
            done: function (e) {
                console.log("updateViaAjax DONE");
                $("#update-student-task-btn").prop("disabled", true);
            }
        });
    }

    function addViaAjax() {
        console.debug("addViaAjax")
        var student = {
            "firstName": $("#firstName").val(),
            "lastName": $("#lastName").val(),
        };
        console.debug(student);

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/student-task-manager/student/create",
            data: JSON.stringify(student),
            dataType: 'json',
            timeout: 100000,
            success: function (data) {
                console.log("addViaAjax SUCCESS: ", data);
                if (data.code == 200) {
                    getStudentTaskViews();
                    $("#add-student-error").text("");
                } else {
                    $("#add-student-error").text(data.errorMessage);
                }
                $("#update-error").text("");
                $("#get-students-error").text("");
            },
            error: function (errorData) {
                console.warn("addViaAjax ERROR: ", errorData);
                $("#add-student-error").text(errorData.errorMessage);
            },
            done: function (e) {
                console.log("addViaAjax DONE");
                $("#add-student-btn").prop("disabled", true);
            }
        });
    }

    function displayStudentTaskViews(studentTaskViews) {
        console.debug("displayStudentTaskViews");
        $('#studentTaskViewsId').html(
            "<div>" +
            "<table class='table'>" +
            "     <tr>" +
            "        <th>Имя</th>" +
            "        <th>Фамилия</th>" +
            "        <th>№ задачи</th>" +
            "     </tr>" +
            studentTaskViews.map(v => {
                const td = value => "<td>" + (value ? value : "") + "</td>";
                return "<tr class=" + (v.taskId == null ? 'success' : 'warning') + ">" +
                    td(v.student.firstName) +
                    td(v.student.lastName) +
                    td(v.taskId) +
                    "</tr>"
            }).join("") +
            "</table>" +
            "</div>"
        );
    }

    function displaySelects(studentTaskViews, tasks) {
        console.debug("displaySelects");
        console.debug(studentTaskViews);
        studentTaskViews.forEach(v => console.debug(v.student.id));
        $("#selectForm").html(
            "<form class='form-horizontal' id='update-student-task-form'>" +
            "     <span>" +
            "         <select id='studentId'>" +
            "         " + studentTaskViews.map(v => "<option value='" + v.student.id + "'>" +
            "         " + v.student.fullName +
            "             </option>").join("") +
            "         </select>" +
            "     </span>" +
            "     <label class='control-label'>Задача</label>" +
            "     <span>" +
            "       <select id='taskId'>" +
            "           " + tasks.map(v => "<option value='" + v.id + "'>" + v.id + "</option>").join("") +
            "       </select>" +
            "     </span>" +
            "     <span>" +
            "         <button type='submit' id='update-student-task-btn' class='btn btn-primary btn-md'>" +
            "             Изменить" +
            "         </button>" +
            "     </span>" +
            "     <span id='update-error' style='color: red;'></span>" +
            "</form>"
        );
        $("#update-student-task-form").submit(function (event) {
            $("#update-student-task-btn").prop("disabled", false);
            event.preventDefault();
            updateViaAjax();
        });
    }
</script>