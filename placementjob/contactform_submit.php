<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

$name = $_POST['name'];
$email = $_POST['email'];
$subject = $_POST['subject'];
$message = $_POST['message'];

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;

require_once __DIR__ . '/vendor/phpmailer/src/Exception.php';
require_once __DIR__ . '/vendor/phpmailer/src/PHPMailer.php';
require_once __DIR__ . '/vendor/phpmailer/src/SMTP.php';

// passing true in constructor enables exceptions in PHPMailer
$mail = new PHPMailer(true);

try {
    // Server settings
    $mail->SMTPDebug = SMTP::DEBUG_SERVER; // for detailed debug output
    $mail->isSMTP();
    $mail->Host = 'anbprojection.com';
    $mail->SMTPAuth = true;
    $mail->SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS;
    $mail->Port = 587;

    $mail->Username = 'support@anbprojection.com'; // YOUR gmail email
    $mail->Password = 'ANB@@##1122'; // YOUR gmail password

    // Sender and recipient settings
    $mail->setFrom($email, $name);
    $mail->addAddress('support@anbprojection.com', $name);
    $mail->addReplyTo($email, $name); // to set the reply to

    // Setting the email content
    $mail->IsHTML(true);
    $mail->Subject = "Inquiry For Contact Us";
    $mail->Body = '<table>
					<tr>
						<th>Name</th>
						<td>'.$name.'</td>
					</tr>
					<tr>
						<th>Email</th>
						<td>'.$email.'</td>
					</tr>
					<tr>
						<th>Subject</th>
						<td>'.$subject.'</td>
					</tr>
					<tr>
						<th>Message</th>
						<td>'.$message.'</td>
					</tr>
					</table>
					</body>';
    //$mail->AltBody = 'Plain text message body for non-HTML email client. Gmail SMTP email body.';

    $mail->send();
    echo "Email message sent.";
	
	//header('Location: https://anbprojection.com/');
	
} catch (Exception $e) {
    echo "Error in sending email. Mailer Error: {$mail->ErrorInfo}";
}

?>