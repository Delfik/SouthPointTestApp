package main.views;

/**
 * Created by delf on 1/21/16.
 * Тот костыль, о котором я говорил
 */
public class ApplicationView {
    public static String mainView(){
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset='UTF-8'>\n" +
                "    <title>Морда тестового приложения</title>\n" +
                "    <script src='https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js'></script>\n" +
                "</head>\n" +
                "<body onload='init()'>\n" +
                "<!--На фронтенде решил, честно, не особо заморачиваться. Поэтому, никакого дизайна не делал.\n" +
                "Из доп. библиотек только jQuery по Google CDN-->\n" +
                "<div>\n" +
                "    <h3>Просмотр существующих контактов:</h3>\n" +
                "    <div id='existingUsersList'></div>\n" +
                "    <div>\n" +
                "        <span>Фильтр по имени:</span>\n" +
                "        <span>\n" +
                "            <input type='text' id='userNameFilterText'>\n" +
                "        </span>\n" +
                "    </div>\n" +
                "    <span id='parsedJsonOfUserList'></span>\n" +
                "</div>\n" +
                "<div>\n" +
                "    <h3>Просмотр существующих групп:</h3>\n" +
                "    <div id='existingGroupsList'></div>\n" +
                "    <span id='parsedJsonOfGroupList'></span>\n" +
                "</div>\n" +
                "<div id='createContactDiv'>\n" +
                "    <h3>Создание нового контакта</h3>\n" +
                "    <form id='CreateNewUserForm'>\n" +
                "        <label>Введите имя: <input type='text' id='createNewUserName' name='name' maxlength='30'></label><br>\n" +
                "        <label>Введите телефон: <input type='text' id='createNewUserPhone' name='phone' maxlength='30'></label><br>\n" +
                "        <label for='userGroupList'>Выберите группы контакта:</label>\n" +
                "        <select name='groups' id='userGroupList' size='5' multiple style='margin-top: 20px; min-width: 8%'>\n" +
                "        </select><br>\n" +
                "        <input type='button' value='Сбросить выбор групп' id='deselectUserGroupList'>\n" +
                "        <input type='submit' value='Создать контакт'><br>\n" +
                "        <span id = 'newUserSuccCreation'></span>\n" +
                "    </form>\n" +
                "</div>\n" +
                "<div id='createGroupDiv'>\n" +
                "    <h3>Создание группы</h3>\n" +
                "    <form id='CreateNewGroupForm'>\n" +
                "        <label>Введите имя группы: <input type='text' id='createNewGroupName' name='name' maxlength='30'></label><br>\n" +
                "        <input type='submit' value='Создать группу'><br>\n" +
                "        <span id = 'newGroupSuccCreation'></span>\n" +
                "    </form>\n" +
                "</div>\n" +
                "<div id='editContactDiv'>\n" +
                "    <h3>Редактирование контакта</h3>\n" +
                "    <form id='EditExistingUserForm'>\n" +
                "        <input type='hidden' id='editableUserId' name='id'>\n" +
                "        <label>Введите новое имя: <input type='text' id='editUserName' name='name' maxlength='30'></label><br>\n" +
                "        <label>Введите новый телефон: <input type='text' id='editUserPhone' name='phone' maxlength='30'></label><br>\n" +
                "        <label for='editUserGroupList'>Выберите группы контакта:</label>\n" +
                "        <select name='groups' id='editUserGroupList' size='5' multiple style='margin-top: 20px; min-width: 8%'>\n" +
                "        </select><br>\n" +
                "        <input type='button' value='Сбросить выбор групп' id='deselectEditUserGroupList'><br>\n" +
                "        <input type='button' id='dontSaveEditContact' value='Отменить редактирование' onclick='defaultDivsVisibility()'><br>\n" +
                "        <input type='submit' value='Редактировать контакт '><br>\n" +
                "    </form>\n" +
                "</div>\n" +
                "<div id='editGroupDiv'>\n" +
                "    <h3>Редактирование группы</h3>\n" +
                "    <form id='EditExistingGroupForm'>\n" +
                "        <input type='hidden' id='editableGroupId' name='id'>\n" +
                "        <label>Введите новое имя группы: <input type='text' id='editGroupNewName' name='name' maxlength='30'></label><br>\n" +
                "        <input type='button' id='dontSaveEditGroup' value='Отменить редактирование' onclick='defaultDivsVisibility()'><br>\n" +
                "        <input type='submit' value='Сохранить новое имя'><br>\n" +
                "    </form>\n" +
                "    <div>\n" +
                "        <span>Контакты в группе:</span><br>\n" +
                "        <div id='usersInGroupList'></div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "    <script>\n" +
                "        //Unique id for different purposes\n" +
                "        var developmentPrefix = 'http://localhost:8080';\n" +
                "        var idForGroupListTableRow = 'groupListRow';\n" +
                "        var idForUserListTableRow = 'userListTableRow';\n" +
                "        var idForUserOptionGroup = 'userOptionGroupId';\n" +
                "        var idForGroupNameRow = 'groupNameUniqueId';\n" +
                "        var idForExcludeUserFromGroup = 'idForExcludeUserFromGroup';\n" +
                "        var idForUserName = 'userNameId';\n" +
                "        var idForUserPhone = 'userPhoneId';\n" +
                "        var idForEditUserListOption = 'idForEditUserListOption';\n" +
                "        \n" +
                "        //initial function\n" +
                "        function init(){\n" +
                "            $('#parsedJsonOfGroupList').hide(); //show if need to see json of full group list. After deletion contains old data!\n" +
                "            $('#parsedJsonOfUserList').hide(); //show if need to see json of full users list. After deletion contains old data!\n" +
                "            $('#newUserSuccCreation').hide(); //Message about successful user creation\n" +
                "            $('#newGroupSuccCreation').hide(); //Message about successful user creation\n" +
                "            fullUpdateUsersList(); //Full update of user list\n" +
                "            fullUpdateGroupList(); //full update of group list\n" +
                "            defaultDivsVisibility(); //Default visibility for different divs\n" +
                "            $('#deselectUserGroupList').on('click', function(){ //Clear group list selection in user creation dialog\n" +
                "                $('#userGroupList option').prop('selected', false);\n" +
                "            });\n" +
                "            $('#deselectEditUserGroupList').on('click', function(){ //Clear group list selection in user edit dialog\n" +
                "                $('#editUserGroupList option').prop('selected', false);\n" +
                "            });\n" +
                "            $('#userNameFilterText').on('keyup', function() { //filter by name implementation\n" +
                "                var text = $(this).val();\n" +
                "                var filterUserQuery = new XMLHttpRequest();\n" +
                "                if(text!='')filterUserQuery.open('GET', developmentPrefix+'/users/filter/'+text, true);\n" +
                "                else filterUserQuery.open('GET', developmentPrefix+'/users/all', true);\n" +
                "                filterUserQuery.send();\n" +
                "                filterUserQuery.onreadystatechange = function() {\n" +
                "                    if(filterUserQuery.readyState!=4)return;\n" +
                "                    var parsed = generateUserList(filterUserQuery);\n" +
                "                    $('#existingUsersList').html(parsed);\n" +
                "                };\n" +
                "            });\n" +
                "        }\n" +
                "        \n" +
                "        //Setup default visibility for divs\n" +
                "        function defaultDivsVisibility(){\n" +
                "            $('#editContactDiv').hide();\n" +
                "            $('#editGroupDiv').hide();\n" +
                "            $('#createContactDiv').show();\n" +
                "            $('#createGroupDiv').show();\n" +
                "        }\n" +
                "\n" +
                "        //Copy & pasted function to serialize form data into JSON\n" +
                "        (function ($) {\n" +
                "            $.fn.serializeFormJSON = function () {\n" +
                "                var o = {};\n" +
                "                var a = this.serializeArray();\n" +
                "                $.each(a, function () {\n" +
                "                    if (o[this.name]) {\n" +
                "                        if (!o[this.name].push) {\n" +
                "                            o[this.name] = [o[this.name]];\n" +
                "                        }\n" +
                "                        o[this.name].push(this.value || '');\n" +
                "                    } else {\n" +
                "                        o[this.name] = this.value || '';\n" +
                "                    }\n" +
                "                });\n" +
                "                return o;\n" +
                "            };\n" +
                "        })(jQuery);\n" +
                "        \n" +
                "        //fill data for Edit Group div\n" +
                "        function processEditGroup(id){\n" +
                "            $('#editableGroupId').val(id); //saved id for group\n" +
                "            $('#editGroupNewName').val($('#'+idForGroupNameRow+id).text()); //saved old name for group\n" +
                "            var groupUserQuery = new XMLHttpRequest();\n" +
                "            groupUserQuery.open('GET', developmentPrefix+'/groups/users/'+id, true);\n" +
                "            groupUserQuery.send();\n" +
                "            groupUserQuery.onreadystatechange = function(){\n" +
                "                if(groupUserQuery.readyState!=4)return;\n" +
                "                var result = JSON.parse(groupUserQuery.responseText);\n" +
                "                var parsed = '<table>';\n" +
                "                parsed+= '<thead><tr><th>Имя контакта (телефон)</th><th>Действия</th></tr></thead>';\n" +
                "                for(var i=0;i<result.length;i++) {\n" +
                "                    var obj = result[i];\n" +
                "                    parsed+= '<tr id=\\''+idForExcludeUserFromGroup+obj['id']+'\\'>';\n" +
                "                    parsed += '<td>'+obj['name']+' ('+obj['phone']+')'+'</td>';\n" +
                "                    parsed += '<td>'+'<a href=\\'javascript:excludeUserFromGroup('+id+','+obj['id']+')\\'>'+\n" +
                "                            'Исключить из группы'+'</a></td>';\n" +
                "                    parsed += '</tr>'\n" +
                "                }\n" +
                "                parsed +='</table>';\n" +
                "                $('#usersInGroupList').html(parsed);\n" +
                "            };\n" +
                "            defaultDivsVisibility();\n" +
                "            $('#editGroupDiv').show();\n" +
                "        }\n" +
                "        \n" +
                "        //Process query to exclude user with id = uid from group with id = gid\n" +
                "        function excludeUserFromGroup(gid, uid){\n" +
                "            var query = new XMLHttpRequest();\n" +
                "            query.open('GET', developmentPrefix+'/groups/users/exclude/'+uid+'/'+gid, true);\n" +
                "            query.send();\n" +
                "            query.onreadystatechange = function() {\n" +
                "                if (query.readyState != 4)return;\n" +
                "                removeElementById(idForExcludeUserFromGroup+uid);\n" +
                "                fullUpdateUsersList();\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        $('#EditExistingGroupForm').submit(function (e) { //Start of EditExistingGroupForm submission\n" +
                "            e.preventDefault();\n" +
                "            processTextReplace('#editGroupNewName');\n" +
                "            var data = $(this).serializeFormJSON();\n" +
                "            processUpdateGroupRequest(data)\n" +
                "        });\n" +
                "        \n" +
                "        //Process query to update group\n" +
                "        function processUpdateGroupRequest(data){\n" +
                "            var xhttp = new XMLHttpRequest();\n" +
                "            xhttp.open('GET',developmentPrefix+'/groups/update/'+JSON.stringify(data), true);\n" +
                "            xhttp.send();\n" +
                "            xhttp.onreadystatechange = function(){\n" +
                "                if(xhttp.readyState!=4)return;\n" +
                "                if(xhttp.status!=200){\n" +
                "                    console.log('Error during processing HTTP request of update group record');\n" +
                "                } else {\n" +
                "                    data = JSON.parse(xhttp.responseText);\n" +
                "                    fullUpdateUsersList();\n" +
                "                    fullUpdateGroupList();\n" +
                "                    //Можно было бы реализовать неполное обновление, а только измененной группы, но тут решил,\n" +
                "                    //что лучше пораньше отправить выполненное задание\n" +
                "                }\n" +
                "            };\n" +
                "            defaultDivsVisibility();\n" +
                "        }\n" +
                "\n" +
                "        $('#EditExistingUserForm').submit(function (e) { //Start of EditExistingUserForm submission\n" +
                "            e.preventDefault();\n" +
                "            //manually form JSON\n" +
                "            var data = {};\n" +
                "            var groups = [];\n" +
                "            var array = [];\n" +
                "            $('#editUserGroupList option:selected').each(function() { //form groups aray\n" +
                "                array.push($(this).attr('name'));\n" +
                "            });\n" +
                "            for(var i=0;i<array.length;i++)\n" +
                "                groups.push({'id':array[i]});\n" +
                "            data['id'] = $('#editableUserId').val();\n" +
                "            processTextReplace('#editUserName');\n" +
                "            data['name'] = $('#editUserName').val();\n" +
                "            processTextReplace('#editUserPhone');\n" +
                "            data['phone'] = $('#editUserPhone').val();\n" +
                "            data['groups'] = groups;\n" +
                "            processEditUserRequest(data);\n" +
                "        });\n" +
                "        \n" +
                "        //Process query to edit user\n" +
                "        function processEditUserRequest(data){\n" +
                "            var xhttp = new XMLHttpRequest();\n" +
                "            xhttp.open('GET',developmentPrefix+'/users/edit/'+JSON.stringify(data), true);\n" +
                "            xhttp.send();\n" +
                "            xhttp.onreadystatechange = function() {\n" +
                "                if (xhttp.readyState != 4)return;\n" +
                "                if (xhttp.status != 200) {\n" +
                "                    console.log('Error during processing HTTP request of CreateNewUserForm');\n" +
                "                } else {\n" +
                "                    fullUpdateUsersList();\n" +
                "                }\n" +
                "            };\n" +
                "            defaultDivsVisibility();\n" +
                "        }\n" +
                "\n" +
                "        $('#CreateNewUserForm').submit(function (e) { //Start of CreateNewUserForm submission\n" +
                "            e.preventDefault();\n" +
                "            //Manually form JSON\n" +
                "            var data = {};\n" +
                "            var groups = [];\n" +
                "            var array = [];\n" +
                "            $('#userGroupList option:selected').each(function() {\n" +
                "                array.push($(this).attr('name'));\n" +
                "            });\n" +
                "            for(var i=0;i<array.length;i++)\n" +
                "                groups.push({'id':array[i]});\n" +
                "            processTextReplace('#createNewUserName');\n" +
                "            data['name'] = $('#createNewUserName').val();\n" +
                "            processTextReplace('#createNewUserPhone');\n" +
                "            data['phone'] = $('#createNewUserPhone').val();\n" +
                "            data['groups'] = groups;\n" +
                "            processNewUserRequest(data);\n" +
                "        });\n" +
                "        \n" +
                "        //Process query to edit new user\n" +
                "        function processNewUserRequest(data){\n" +
                "            var xhttp = new XMLHttpRequest();\n" +
                "            xhttp.open('GET',developmentPrefix+'/users/new/'+JSON.stringify(data), true);\n" +
                "            xhttp.send();\n" +
                "            xhttp.onreadystatechange = function() {\n" +
                "                if (xhttp.readyState != 4)return;\n" +
                "                if (xhttp.status != 200) {\n" +
                "                    console.log('Error during processing HTTP request of CreateNewUserForm');\n" +
                "                    $('#newUserSuccCreation').html('Error during processing HTTP request of CreateNewUserForm:<br>' + xhttp.responseText);\n" +
                "                } else {\n" +
                "                    $('#newUserSuccCreation').html('User with name <b>' + data['name'] + '</b> and phone <b>' + data['phone'] + '</b> successfully created'/* + xhttp.responseText*/);\n" +
                "                    $('#userGroupList option').prop('selected', false);\n" +
                "                    fullUpdateUsersList();\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "        \n" +
                "        //Process request of all users\n" +
                "        function fullUpdateUsersList(){\n" +
                "            var allUsersQuery = new XMLHttpRequest();\n" +
                "            allUsersQuery.open('GET', developmentPrefix+'/users/all', true);\n" +
                "            allUsersQuery.send();\n" +
                "            allUsersQuery.onreadystatechange = function(){\n" +
                "                if(allUsersQuery.readyState!=4)return;\n" +
                "                $('#parsedJsonOfUserList').text(allUsersQuery.responseText);\n" +
                "                var parsed = generateUserList(allUsersQuery);\n" +
                "                $('#existingUsersList').html(parsed);\n" +
                "            }\n" +
                "        }\n" +
                "        \n" +
                "        //Generate user list based on server response (used in request all user and user filtering)\n" +
                "        function generateUserList(request){\n" +
                "            var result = JSON.parse(request.responseText);\n" +
                "            var parsed = '<table>';\n" +
                "            parsed+= '<thead><tr><th>Имя контакта</th><th>Телефон</th><th>Группы</th>'+\n" +
                "                    '<th colspan=\\'2\\'>Действия</th></tr></thead>';\n" +
                "            for(var i=0;i<result.length;i++){\n" +
                "                var obj = result[i];\n" +
                "                parsed+='<tr id = \\''+idForUserListTableRow+obj['id']+'\\'>';\n" +
                "                parsed += '<td id=\\''+idForUserName+obj['id']+'\\'>'+obj['name']+'</td>';\n" +
                "                parsed += '<td id=\\''+idForUserPhone+obj['id']+'\\'>'+obj['phone']+'</td>';\n" +
                "                parsed += '<td>';\n" +
                "                var gr = obj['groups'];\n" +
                "                for(var j = 0; j<gr.length;j++){\n" +
                "                    parsed += gr[j]['name'];\n" +
                "                    if(j!=gr.length-1){\n" +
                "                        parsed+=', ';\n" +
                "                    }\n" +
                "                }\n" +
                "                parsed += '</td>';\n" +
                "                parsed += '<td>'+'<a href=\\''+'javascript:processDeleteUserById('+obj['id']+')'+'\\'>'+\n" +
                "                        'удалить</a>'+'</td>';\n" +
                "                parsed += '<td>'+'<a href=\\''+'javascript:processEditUser('+obj['id']+')'+'\\'>'+\n" +
                "                        'редактировать</a>'+'</td>';\n" +
                "                parsed+='</tr>';\n" +
                "            }\n" +
                "            parsed+='</table>';\n" +
                "            return parsed;\n" +
                "        }\n" +
                "\n" +
                "        function processEditUser(id){\n" +
                "            //fill edit user form data\n" +
                "            $('#editableUserId').val(id); //saved id for user\n" +
                "            $('#editUserName').val($('#'+idForUserName+id).text()); //saved old name for user\n" +
                "            $('#editUserPhone').val($('#'+idForUserPhone+id).text());\n" +
                "            var groupsQuery = new XMLHttpRequest();\n" +
                "            groupsQuery.open('GET', developmentPrefix+'/users/groups/'+id, true);\n" +
                "            groupsQuery.send();\n" +
                "            groupsQuery.onreadystatechange = function(){\n" +
                "                if(groupsQuery.readyState!=4)return;\n" +
                "                var groups = JSON.parse(groupsQuery.responseText);\n" +
                "                $('#editUserGroupList option').prop('selected', false);\n" +
                "                for(var i = 0; i < groups.length; i++){\n" +
                "                    var group = groups[i];\n" +
                "                    //Select user groups:\n" +
                "                    $('#editUserGroupList option[id=\\''+idForEditUserListOption+group['id']+'\\']').prop('selected', 'selected').change();\n" +
                "                }\n" +
                "            };\n" +
                "            defaultDivsVisibility();\n" +
                "            $('#editContactDiv').show();\n" +
                "        }\n" +
                "\n" +
                "        $('#CreateNewGroupForm').submit(function (e) { //Start of CreateNewGroupForm submission\n" +
                "            e.preventDefault();\n" +
                "            processTextReplace('#createNewGroupName');\n" +
                "            var data = $(this).serializeFormJSON();\n" +
                "            processNewGroupRequest(data)\n" +
                "        });\n" +
                "        \n" +
                "        //Process query to create new group\n" +
                "        function processNewGroupRequest(data){\n" +
                "            var xhttp = new XMLHttpRequest();\n" +
                "            xhttp.open('GET',developmentPrefix+'/groups/new/'+JSON.stringify(data), true);\n" +
                "            xhttp.send();\n" +
                "            xhttp.onreadystatechange = function(){\n" +
                "                if(xhttp.readyState!=4)return;\n" +
                "                if(xhttp.status!=200){\n" +
                "                    console.log('Error during processing HTTP request of CreateNewGroupForm');\n" +
                "                    $('#newGroupSuccCreation').text('Error during processing HTTP request of CreateNewGroupForm:\\n'+xhttp.responseText);\n" +
                "                } else {\n" +
                "                    $('#newGroupSuccCreation').html('Group with name <b>'+data['name']+'</b> successfully created'/*+xhttp.responseText*/);\n" +
                "                    data = JSON.parse(xhttp.responseText);\n" +
                "                    fullUpdateGroupList(); //мб реализовать неполное обновление\n" +
                "                }\n" +
                "            };\n" +
                "        }\n" +
                "        \n" +
                "        //Update group list\n" +
                "        function fullUpdateGroupList(){\n" +
                "            var allGroupsQuery = new XMLHttpRequest();\n" +
                "            allGroupsQuery.open('GET', developmentPrefix+'/groups/all', true);\n" +
                "            allGroupsQuery.send();\n" +
                "            allGroupsQuery.onreadystatechange = function(){\n" +
                "                if(allGroupsQuery.readyState!=4)return\n" +
                "                $('#userGroupList option').remove();\n" +
                "                $('#editUserGroupList option').remove();\n" +
                "                $('#parsedJsonOfGroupList').text(allGroupsQuery.responseText);\n" +
                "                var result = JSON.parse(allGroupsQuery.responseText);\n" +
                "                var parsed = '<table>';\n" +
                "                parsed+= '<thead><tr><th>Название группы</th><th colspan=\\'2\\'>Действия</th></tr></thead>';\n" +
                "                for(var i=0;i<result.length;i++){\n" +
                "                    var obj = result[i];\n" +
                "                    parsed+='<tr id = \\''+idForGroupListTableRow+obj['id']+'\\'>';\n" +
                "                    parsed += '<td id=\\''+idForGroupNameRow+obj['id']+'\\'>'+obj['name']+'</td>';\n" +
                "                    parsed += '<td>'+'<a href=\\''+'javascript:processDeleteGroupById('+obj['id']+')'+'\\'>'+\n" +
                "                            'удалить</a>'+'</td>';\n" +
                "                    parsed += '<td>'+'<a href=\\''+'javascript:processEditGroup('+obj['id']+')'+'\\'>'+\n" +
                "                            'редактировать</a>'+'</td>';\n" +
                "                    parsed+='</tr>';\n" +
                "                    $('#userGroupList').append('<option name=\\''+obj['id']+'\\' id=\\''\n" +
                "                            +idForUserOptionGroup+obj['id']+'\\'>'+obj['name']+'</option>>');\n" +
                "                    $('#editUserGroupList').append('<option name=\\''+obj['id']+'\\' id=\\''\n" +
                "                            +idForEditUserListOption+obj['id']+'\\'>'+obj['name']+'</option>>');\n" +
                "                }\n" +
                "                parsed+='</table>';\n" +
                "                $('#existingGroupsList').html(parsed);\n" +
                "            }\n" +
                "        }\n" +
                "        \n" +
                "        //Process query to delete group by id\n" +
                "        function processDeleteGroupById(id){\n" +
                "            var xhttp = new XMLHttpRequest();\n" +
                "            xhttp.open('GET', developmentPrefix+'/groups/delete/'+id, true);\n" +
                "            xhttp.send();\n" +
                "            xhttp.onreadystatechange = function(){\n" +
                "                if(xhttp.readyState!=4)return;\n" +
                "                if(xhttp.status!=200){\n" +
                "                    console.log('Error during processing HTTP request of deleteGroupById');\n" +
                "                } else {\n" +
                "                    removeElementById(idForGroupListTableRow+id); //удаление только нужной строки из DOM таблицы\n" +
                "                    removeElementById(idForUserOptionGroup+id); //из DOM для списка групп\n" +
                "                    removeElementById(idForUserOptionGroup+id); //из опшнов\n" +
                "                    fullUpdateUsersList(); //обновить список юзеров в соответствии с новыми данными...\n" +
                "                }\n" +
                "            };\n" +
                "        }\n" +
                "        \n" +
                "        //Process query to delete user by id\n" +
                "        function processDeleteUserById(id){\n" +
                "            var xhttp = new XMLHttpRequest();\n" +
                "            xhttp.open('GET', developmentPrefix+'/users/delete/'+id, true);\n" +
                "            xhttp.send();\n" +
                "            xhttp.onreadystatechange = function(){\n" +
                "                if(xhttp.readyState!=4)return;\n" +
                "                if(xhttp.status!=200){\n" +
                "                    console.log('Error during processing HTTP request of deleteUserById');\n" +
                "                } else {\n" +
                "                    removeElementById(idForUserListTableRow+id); //удаление только нужной строки из DOM таблицы\n" +
                "                }\n" +
                "            };\n" +
                "        }\n" +
                "        \n" +
                "        //Remove element by id\n" +
                "        function removeElementById(id){\n" +
                "            $('#'+id).remove();\n" +
                "        }\n" +
                "\n" +
                "        //Simple filter for imput\n" +
                "        function specialReplace(data){\n" +
                "            return data.replace(/[^a-zA-ZА-Яа-я0-9+\\s]/gi, '').toString();\n" +
                "        }\n" +
                "        \n" +
                "        //Process filter\n" +
                "        function processTextReplace(id){\n" +
                "            $(id).val(specialReplace($(id).val()));\n" +
                "        }\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>";
    }
}
