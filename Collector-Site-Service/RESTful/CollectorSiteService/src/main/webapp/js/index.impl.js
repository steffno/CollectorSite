var bearerauth = null;

function loggedAREA()
{
    console.log(bearerauth);
    return '<div id="logged-area" class="login_part_text text-center" style="width: 100%">' +
            ' <div class="login_part_text_iner">' +
            '     <ul class="nav nav-pills">' +
            '          <li class="nav-item">' +
            '              <a id="logged-cleaner" class="nav-link">Clean</a>' +
            '          </li>' +
            '          <li class="nav-item">' +
            '              <a id="logged-logout" class="nav-link">Logout</a>' +
            '          </li>' +
            '          <li class="nav-item">' +
            '              <a id="logged-mycollections" class="nav-link" title="Elenco collezioni dell’utente (Private)">Collections</a>' +
            '          </li>' +
            '          <li class="nav-item">' +
            '              <a id="logged-records" class="nav-link" title="Elenco dei dischi in una collezione (Private)">Records</a>' +
            '          </li>' +
            '          <li class="nav-item">' +
            '              <a id="logged-record" class="nav-link" title="Dettagli singolo disco contenuto in una collezione (Private)">Record</a>' +
            '          </li>' +
            '          <li class="nav-item">' +
            '              <a id="logged-coll-info" class="nav-link padding_top_10" title="Estrazione di alcune statistiche (Private)">CollectionInfo</a>' +
            '          </li>' +
            '          <li class="nav-item">' +
            '              <a id="logged-coll-shared" class="nav-link padding_top_10" title="Elenco collezioni condivise con un utente (kama and mastro)">Shared</a>' +
            '          </li>' +
            '          <li class="nav-item">' +
            '              <a id="logged-insert" class="nav-link padding_top_10" title="Inserimento di un nuovo disco in una delle collezioni dell’utente (PostData Predefinito)">Insert</a>' +
            '          </li>' +
            '          <li class="nav-item">' +
            '              <a id="logged-modify" class="nav-link padding_top_10" title="Aggiornamento di un disco in una collezione personale dell’utente (PostData Predefinito)">Modify</a>' +
            '          </li>' +
            '       </ul>' +
            '       <div class="">' +
            '           <textarea style="white-space: nowrap; resize: none; margin-top: 3%" class="form-control" id="logged-path" rows="2" readonly></textarea>' +
            '           <textarea style="resize: none; margin-top: 3%" class="form-control" id="logged-res" rows="25" readonly></textarea>' +
            '       </div>' +
            '       <div class="">' +
            '           <h6 id="id-area1" hidden></h6>' +
            '           <h6 id="id-area2" hidden></h6>' +
            '       </div>' +
            '   </div>' +
            '</div>';
}

function logHTML()
{
    return '<div id="logarea" class="login_part_form">' +
            '<div class="login_part_form_iner"> ' +
            '<style>.error{color: red}</style>' +
            '    <div><p class="error" id="error"></p></div>' +
            '               <form class="row contact_form" action="" method="post" novalidate="novalidate">' +
            '               <div class="col-md-12 form-group p_star">' +
            '               <input type="text" class="form-control" id="name" name="name" value=""' +
            '                  placeholder="Username" required>' +
            '       </div>' +
            '       <div class="col-md-12 form-group p_star">' +
            '           <input type="password" class="form-control" id="password" name="password" value=""' +
            '              placeholder="Password" required minlength="6">' +
            '   </div>' +
            '   <div class="col-md-12 form-group">' +
            '       <input type="submit" style="text-align: center; color: #fafafa" id="loggerbtn" value="login" class="btn_3">' +
            '</div>' +
            '</form>' +
            '</div>' +
            '</div>';
}


function buildOutput(result)
{
    console.log(result);
    
    var item = '';
    
    try 
    {
        result.forEach(function (elem)
        {
            for (const [key, value] of Object.entries(elem)) 
            {
                if (key === 'id') $('#id-area2').text(value);
                
                if (key === 'length')
                {
                    var val = parseFloat(value).toFixed(2) + " Min";
                    item += key + ': ' + val + '\n';
                } 
                else if (key === 'author')
                {
                    item += key + ':\n';
                    for (const [key1, value1] of Object.entries(value)) 
                        item += '\t' + key1 + ': ' + value1 + '\n';
                } 
                else if (key === 'tracks')
                {
                    item += key + ':\n';
                    for (const [key1, value1] of Object.entries(value)) 
                        for (const [key2, value2] of Object.entries(value1)) 
                            if (key2 === 'length') {
                                var val = parseFloat(value2).toFixed(2) + " Min";
                                item += '\t' + key2 + ': ' + val + '\n';
                            } 
                            else
                                item += '\t' + key2 + ': ' + value2 + '\n';
                } 
                else
                    item += key + ': ' + value + '\n';

            }

            item += '---------------------------------\n';
        });
    }
    catch (TypeError) 
    {
        result = [result];
        item = buildOutput(result);
    }

    
    return item;
}


function bindNewActions()
{
    $('#logged-logout').on('click', function ()
    {
        $.ajax({
            url: 'http://localhost:8080/CollectorSiteService/rest/auth/logout',
            type: 'DELETE',
            contentType: 'application/json',
            headers: {
                'Authorization': 'Bearer ' + bearerauth
            },
            statusCode: {
                401: function () {
                    console.log('error');
                }
            }
        }).done(function (result)
        {
            if (result === 'sloggato')
            {
                bindEntryActions();
            }
        });
    });

    $('#logged-cleaner').on('click', function ()
    {
        $('#logged-res').text('');
        $('#logged-path').text('');
    });

    $('#logged-mycollections').on('click', function () {

        var _url = 'http://localhost:8080/CollectorSiteService/rest/collections/mycollection';
        $('#logged-path').text('');
        $('#logged-path').text(_url);

        $.ajax({
            url: _url,
            type: 'GET',
            contentType: 'application/json',
            headers: {
                'Authorization': 'Bearer ' + bearerauth
            }
        }).done(function (result)
        {
            console.log(result);
            $('#logged-res').text('');

            result.forEach(function (elem)
            {
                var item = '';
                for (const [key, value] of Object.entries(elem)) 
                {
                    item += key + ': ' + value + '\n';
                    if (key === 'id')
                        $('#id-area1').text(value);
                }

                item += '---------------------------------';
                $('#logged-res').text($('#logged-res').text() === "" ? item : $('#logged-res').text() + '\n' + item);
            });
        });
    });

    $('#logged-records').on('click', function () {
        var id = $('#id-area1').text();

        var _url = 'http://localhost:8080/CollectorSiteService/rest/collections/mycollection/' + id + '/records';
        $('#logged-path').text('');
        $('#logged-path').text(_url);

        $.ajax({
            url: _url,
            type: 'GET',
            contentType: 'application/json',
            headers: {
                'Authorization': 'Bearer ' + bearerauth
            }
        }).done(function (result)
        {
            $('#logged-res').text('');
            
            $('#logged-res').text(buildOutput(result));
        });
    });

    $('#logged-record').on('click', function () {
        var id1 = $('#id-area1').text();
        var id2 = $('#id-area2').text();


        var _url = 'http://localhost:8080/CollectorSiteService/rest/collections/mycollection/' + id1 + '/records/' + id2;
        $('#logged-path').text('');
        $('#logged-path').text(_url);

        $.ajax({
            url: _url,
            type: 'GET',
            contentType: 'application/json',
            headers: {
                'Authorization': 'Bearer ' + bearerauth
            }
        }).done(function (result)
        {
            $('#logged-res').text('');

            $('#logged-res').text(buildOutput(result));
        });
    });


    $('#logged-coll-info').on('click', function () {
        var id1 = $('#id-area1').text();

        var _url = 'http://localhost:8080/CollectorSiteService/rest/collections/mycollection/' + id1 + '/stats';
        $('#logged-path').text('');
        $('#logged-path').text(_url);

        $.ajax({
            url: _url,
            type: 'GET',
            contentType: 'application/json',
            headers: {
                'Authorization': 'Bearer ' + bearerauth
            }
        }).done(function (result)
        {
            $('#logged-res').text('');

            var item = '';
            for (const [key, value] of Object.entries(result)) {

                if (key === 'authors')
                {
                    item += key + ':\n';
                    for (const [key1, value1] of Object.entries(value)) {
                        for (const [key2, value2] of Object.entries(value1)) {
                            item += '\t' + key2 + ': ' + value2 + '\n';
                        }
                        item += '\t-------------------------------\n';
                    }
                } else if (key === 'genreCount')
                {
                    item += key + ':\n';
                    for (const [key1, value1] of Object.entries(value)) {
                        item += '\t' + key1 + ': ' + value1 + '\n';
                    }
                } else
                    item += key + ': ' + value + '\n';

            }
            $('#logged-res').text(item);

        });
    });

    $('#logged-coll-shared').on('click', function () {

        var _url = 'http://localhost:8080/CollectorSiteService/rest/collections/sharedcollection/0';
        $('#logged-path').text('');
        $('#logged-path').text(_url);

        $.ajax({
            url: _url,
            type: 'GET',
            contentType: 'application/json',
            headers: {
                'Authorization': 'Bearer ' + bearerauth
            }
        }).done(function (result)
        {
            $('#logged-res').text('');

            $('#logged-res').text(buildOutput(result));
        });
    });


    $('#logged-insert').on('click', function () {
        var id0 = $('#id-area1').text();
        var _data = {
                    "id": 500,
                    "title": "Record 30",
                    "trackCount": 10,
                    "author": {
                        "name": "AvengedSevenfold",
                        "recordNo": 7,
                        "bio": "Metal rules",
                        "band": true,
                        "gtype": "Metal"
                    },
                    "date": "2022-05-12",
                    "length": 23.954458,
                    "tracks": [
                        {
                            "title": "Track 1",
                            "length": 2.1603205
                        },
                        {
                            "title": "Track 2",
                            "length": 4.575297
                        },
                        {
                            "title": "Track 3",
                            "length": 2.4774232
                        },
                        {
                            "title": "Track 4",
                            "length": 0.21846414
                        },
                        {
                            "title": "Track 5",
                            "length": 1.3590252
                        },
                        {
                            "title": "Track 6",
                            "length": 1.2146521
                        },
                        {
                            "title": "Track 7",
                            "length": 4.2074556
                        },
                        {
                            "title": "Track 8",
                            "length": 3.4231873
                        },
                        {
                            "title": "Track 9",
                            "length": 1.2201679
                        },
                        {
                            "title": "Track 10",
                            "length": 3.0984645
                        }
                    ]
                };

        var _url = 'http://localhost:8080/CollectorSiteService/rest/collections/' + id0 + '/newrecord';
        $('#logged-path').text('');
        $('#logged-path').text(_url);

        $.ajax({
            url: _url,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(_data),
            headers: {
                'Authorization': 'Bearer ' + bearerauth
            }
        }).done(function (result)
        {
            $('#logged-res').text('');

            $('#logged-res').text('Added to kama\'s shared collectionYZ:\nRecord 30 by AvengedSevenfold');
        });
    });
    
    $('#logged-modify').on('click', function () {
        var id0 = $('#id-area1').text();
        var _data = {
                    "id": 500,
                    "title": "Record 30 E LODE",
                    "trackCount": 10,
                    "author": {
                        "name": "AvengedSevenfold",
                        "recordNo": 7,
                        "bio": "Metal rules",
                        "band": true,
                        "gtype": "Metal"
                    },
                    "date": "2022-05-12",
                    "length": 23.954458,
                    "tracks": [
                        {
                            "title": "Track 1",
                            "length": 2.1603205
                        },
                        {
                            "title": "Track 2",
                            "length": 4.575297
                        },
                        {
                            "title": "Track 3",
                            "length": 2.4774232
                        },
                        {
                            "title": "Track 4",
                            "length": 0.21846414
                        },
                        {
                            "title": "Track 5",
                            "length": 1.3590252
                        }
                    ]
                };

        var _url = 'http://localhost:8080/CollectorSiteService/rest/collections/' + id0 + '/modifyrecord';
        $('#logged-path').text('');
        $('#logged-path').text(_url);

        $.ajax({
            url: _url,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(_data),
            headers: {
                'Authorization': 'Bearer ' + bearerauth
            }
        }).done(function (result)
        {
            $('#logged-res').text('');
            
            $('#logged-res').text('Modified kama\'s shared collectionYZ Record 30 into:\nRecord 30 E LODE by AvengedSevenfold');
        });
    });
    
    
}

function bindEntryActions()
{
    bearerauth = null;

    $('#no-logged-au-rec').on('click', function () {
        var _url = 'http://localhost:8080/CollectorSiteService/rest/collections/publiccollection/records/AvengedSevenfold';

        $('#no-logged-path').text('');
        $('#no-logged-path').text(_url);

        $.ajax({
            url: _url,
            type: 'GET',
            contentType: 'application/json'
        }).done(function (result)
        {
            $('#no-logged-res').text('');

            $('#no-logged-res').text(buildOutput(result));
        });
    });

    $('#no-logged-authors').on('click', function ()
    {
        var _url = 'http://localhost:8080/CollectorSiteService/rest/authors';
        $('#no-logged-path').text('');
        $('#no-logged-path').text(_url);

        $.ajax({
            url: _url,
            type: 'GET'
        }).done(function (result)
        {
            $('#no-logged-res').text('');
            
            $('#no-logged-res').text(buildOutput(result));
        });
    });

    $('#no-logged-cleaner').on('click', function ()
    {
        $('#no-logged-res').text('');
        $('#no-logged-path').text('');
    });

    $('#logged-area').remove();
    $('#login-area').append(logHTML());
    $('#label-login').text('Esegui il login per le altre operazioni');
    
    $('#loggerbtn').on('click', function (e)
    {
        e.preventDefault();

        var uname = $('#name').val();
        var pwd = $('#password').val();


        $.ajax({
            url: 'http://localhost:8080/CollectorSiteService/rest/auth/login',
            type: 'POST',
            data: {
                username: uname,
                password: pwd
            },
            statusCode: {
                401: function () {
                    $('#error').text('Username e/o password sbagliati');
                }
            }
        }).done(function (result)
        {
            bearerauth = result;
            
            $('#error').text('');
            $('#logarea').remove();
            $('#label-login').text('Login eseguito come: ' + uname);
            $('#login-area').append(loggedAREA());
            
            bindNewActions();
        });
    });

    $('#no-logged-records').on('click', function () {
        var _url = 'http://localhost:8080/CollectorSiteService/rest/collections/publiccollection/0/records';
        $('#no-logged-path').text('');
        $('#no-logged-path').text(_url);

        $.ajax({
            url: _url,
            type: 'GET'
        }).done(function (result)
        {
            $('#no-logged-res').text('');
            $('#no-logged-res').text(buildOutput(result));
            
            result.forEach(function (elem){
                for (const [key, value] of Object.entries(elem)) 
                    if (key === 'id') $('#id-area0').text(value);
            });
        });
    });

    $('#no-logged-record').on('click', function () {
        var id0 = $('#id-area0').text();

        var _url = 'http://localhost:8080/CollectorSiteService/rest/collections/publiccollection/0/records/' + id0;
        $('#no-logged-path').text('');
        $('#no-logged-path').text(_url);

        $.ajax({
            url: _url,
            type: 'GET'
        }).done(function (result)
        {
            $('#no-logged-res').text('');

            $('#no-logged-res').text(buildOutput(result));

        });
    });


    $('#no-logged-search').on('click', function () {

        var _url = 'http://localhost:8080/CollectorSiteService/rest/collections/publiccollection/records?authorQParam=AvengedSevenfold';
        $('#no-logged-path').text('');
        $('#no-logged-path').text(_url);

        $.ajax({
            url: _url,
            type: 'GET'
        }).done(function (result)
        {
            $('#no-logged-res').text('');

            $('#no-logged-res').text(buildOutput(result));
        });
    });

}

$(document).ready(function () {
    bindEntryActions();
});