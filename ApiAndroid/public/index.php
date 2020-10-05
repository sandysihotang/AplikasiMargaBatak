<?php

use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;
use Slim\Http\UploadedFile;

require '../vendor/autoload.php';

require '../includes/DBOperations.php';


$app = new \Slim\App(['settings' => ['displayErrorDetails' => true]]);

$app->post('/books', function (Request $request, Response $response) {

    $uploadedFiles = $request->getUploadedFiles();

    // handle single input with single file upload
    $uploadedFile = $uploadedFiles['cover'];
    if ($uploadedFile->getError() === UPLOAD_ERR_OK) {
        $uploadedFileName = $uploadedFile->getClientFileName();
        $type = $uploadedFile->getClientMediaType();
        $uploadedFile->moveTo('uploads/' . $uploadedFileName);
    }
});

$app->put('/updateUser/{id}', function (Request $request, Response $response, array $args) {
    $id = $args['id'];
    if (!haveEmptyParameters(array('username', 'password', 'firstname', 'lastname'), $request, $response)) {
        $request_data = $request->getParsedBody();
        $username = $request_data['username'];
        $password = $request_data['password'];
        $firstname = $request_data['firstname'];
        $lastname = $request_data['lastname'];
        $db = new DBOperations();
        if ($db->updateUser($id, $username, $password, $firstname, $lastname)) {
            $message = array();
            $message['error'] = false;
            $message['message'] = 'User Updated Successfully';
            $response->write(json_encode($message));

            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(201);
        }
    }
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(422);
});

$app->post('/login', function (Request $request, Response $response) {
    if (!haveEmptyParameters(array('username', 'password'), $request, $response)) {
        $request_data = $request->getParsedBody();
        $username = $request_data['username'];
        $password = $request_data['password'];
        $db = new DBOperations();
        $result = $db->loginUser($username, md5($password));
        if ($result == USER_AUTHENTICATED) {
            $user = $db->getUser($username);
            $response_data = array();
            $response_data['error'] = false;
            $response_data['message'] = 'Login Successfully';
            $response_data['user'] = $user;
            $response->write(json_encode($response_data));

            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);
        } elseif ($result == USER_NOT_FOUND) {
            $response_data = array();
            $response_data['error'] = true;
            $response_data['message'] = 'User Not Exist';
            $response->write(json_encode($response_data));

            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);
        } elseif ($result == PASSWORD_DO_NOT_MATCH) {
            $response_data = array();
            $response_data['error'] = true;
            $response_data['message'] = 'Invalid Credential';
            $response->write(json_encode($response_data));

            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);
        }
    }
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(422);
});

$app->get('/user', function (Request $request, Response $response) {
    $db = new DBOperations();
    $users = $db->getAllUser();
    $response_data = array();
    $response_data['error'] = false;
    $response_data['users'] = $users;
    $response->write(json_encode($response_data));
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(201);
});

$app->get('/marga', function (Request $request, Response $response) {
    $db = new DBOperations();
    $margas = $db->getMarga();
    $response_data = array();
    $response_data['error'] = false;
    $response_data['margas'] = $margas;
    $response->write(json_encode($response_data));
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(201);
});

$app->post('/createMarga', function (Request $request, Response $response) {
    if (!haveEmptyParameters(array('marga', 'cerita'), $request, $response)) {
        $uploadedFiles = $request->getUploadedFiles();

        // handle single input with single file upload
        $uploadedFile = $uploadedFiles['foto'];
        $uploadedFileName = null;
        if ($uploadedFile->getError() === UPLOAD_ERR_OK) {
            $uploadedFileName = $uploadedFile->getClientFileName();
            $type = $uploadedFile->getClientMediaType();
            $uploadedFile->moveTo('uploads/' . $uploadedFileName);
        }

        $request_data = $request->getParsedBody();
        $marga = $request_data['marga'];
        $cerita = $request_data['cerita'];
        $db = new DBOperations();
        $result = $db->createMarga($marga, $cerita, $uploadedFileName);
        if ($result == USER_CREATED) {
            $message = array();
            $message['error'] = false;
            $message['message'] = 'Marga Created Successfully';
            $response->write(json_encode($message));

            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(201);
        } elseif ($result == USER_FAILURE) {
            $message = array();
            $message['error'] = true;
            $message['message'] = 'Some Error occured';
            $response->write(json_encode($message));

            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(422);
        }

    }
});

$app->get('/detail/{id}', function (Request $request, Response $response, array $args) {
    $id = $args['id'];
    $db = new DBOperations();
    $response_data = array();
    $response_data['marga'] = $db->getUserDetail($id);
    $response->write(json_encode($response_data));
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(201);

});
$app->post('/createuser', function (Request $request, Response $response) {

    if (!haveEmptyParameters(array('username', 'password', 'firstname', 'lastname'), $request, $response)) {



        $request_data = $request->getParsedBody();
        $username = $request_data['username'];
        $password = $request_data['password'];
        $firstname = $request_data['firstname'];
        $lastname = $request_data['lastname'];

        $has_password = md5($password);
        $db = new DBOperations();
        $result = $db->createUser($username, $has_password, $firstname, $lastname);

        if ($result == USER_CREATED) {
            $message = array();
            $message['error'] = false;
            $message['message'] = 'User Created Successfully';
            $response->write(json_encode($message));

            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(201);
        } elseif ($result == USER_FAILURE) {
            $message = array();
            $message['error'] = true;
            $message['message'] = 'Some Error occured';
            $response->write(json_encode($message));

            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(422);
        } elseif ($result == USER_EXIST) {
            $message = array();
            $message['error'] = true;
            $message['message'] = 'User Already Exis';
            $response->write(json_encode($message));

            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(422);
        }
    }
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(422);
});

$app->delete('/deleteuser/{id}', function (Request $request, Response $response, array $args) {
    $id = $args['id'];

    $db = new DBOperations();
    $response_data = array();
    if ($db->deleteUser($id)) {
        $response_data['error'] = false;
        $response_data['message'] = "User Has Been Deleted";
    } else {
        $response_data['error'] = false;
        $response_data['message'] = "Try Again";
    }
    $response->write(json_encode($response_data));
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(422);
});

function haveEmptyParameters($requred_params, $request, $response)
{
    $error = false;
    $error_params = '';
    $request_params = $request->getParsedBody();
    foreach ($requred_params as $param) {
        if (!isset($request_params[$param]) || strlen($request_params[$param]) <= 0) {
            $error = true;
            $error_params .= $param . ',';
        }
    }
    if ($error) {
        $error_detail = array();
        $error_detail['error'] = true;
        $error_detail['message'] = 'Required parameters ' . substr($error_params, 0, -1);
        $response->write(json_encode($error_detail));
    }
    return $error;

}

$app->run();
