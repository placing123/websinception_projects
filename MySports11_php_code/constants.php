<?php
defined('BASEPATH') or exit('No direct script access allowed');

/*
|--------------------------------------------------------------------------
| Display Debug backtrace
|--------------------------------------------------------------------------
|
| If set to TRUE, a backtrace will be displayed along with php errors. If
| error_reporting is disabled, the backtrace will not display, regardless
| of this setting
|
*/
defined('SHOW_DEBUG_BACKTRACE') or define('SHOW_DEBUG_BACKTRACE', true);

/*
|--------------------------------------------------------------------------
| File and Directory Modes
|--------------------------------------------------------------------------
|
| These prefs are used when checking and setting modes when working
| with the file system.  The defaults are fine on servers with proper
| security, but you may wish (or even need) to change the values in
| certain environments (Apache running a separate process for each
| user, PHP under CGI with Apache suEXEC, etc.).  Octal values should
| always be used to set the mode correctly.
|
*/
defined('FILE_READ_MODE') or define('FILE_READ_MODE', 0644);
defined('FILE_WRITE_MODE') or define('FILE_WRITE_MODE', 0666);
defined('DIR_READ_MODE') or define('DIR_READ_MODE', 0755);
defined('DIR_WRITE_MODE') or define('DIR_WRITE_MODE', 0755);

/*
|--------------------------------------------------------------------------
| File Stream Modes
|--------------------------------------------------------------------------
|
| These modes are used when working with fopen()/popen()
|
*/
defined('FOPEN_READ') or define('FOPEN_READ', 'rb');
defined('FOPEN_READ_WRITE') or define('FOPEN_READ_WRITE', 'r+b');
defined('FOPEN_WRITE_CREATE_DESTRUCTIVE') or define('FOPEN_WRITE_CREATE_DESTRUCTIVE', 'wb'); // truncates existing file data, use with care
defined('FOPEN_READ_WRITE_CREATE_DESTRUCTIVE') or define('FOPEN_READ_WRITE_CREATE_DESTRUCTIVE', 'w+b'); // truncates existing file data, use with care
defined('FOPEN_WRITE_CREATE') or define('FOPEN_WRITE_CREATE', 'ab');
defined('FOPEN_READ_WRITE_CREATE') or define('FOPEN_READ_WRITE_CREATE', 'a+b');
defined('FOPEN_WRITE_CREATE_STRICT') or define('FOPEN_WRITE_CREATE_STRICT', 'xb');
defined('FOPEN_READ_WRITE_CREATE_STRICT') or define('FOPEN_READ_WRITE_CREATE_STRICT', 'x+b');

/*
|--------------------------------------------------------------------------
| Exit Status Codes
|--------------------------------------------------------------------------
|
| Used to indicate the conditions under which the script is exit()ing.
| While there is no universal standard for error codes, there are some
| broad conventions.  Three such conventions are mentioned below, for
| those who wish to make use of them.  The CodeIgniter defaults were
| chosen for the least overlap with these conventions, while still
| leaving room for others to be defined in future versions and user
| applications.
|
| The three main conventions used for determining exit status codes
| are as follows:
|
|    Standard C/C++ Library (stdlibc):
|       http://www.gnu.org/software/libc/manual/html_node/Exit-Status.html
|       (This link also contains other GNU-specific conventions)
|    BSD sysexits.h:
|       http://www.gsp.com/cgi-bin/man.cgi?section=3&topic=sysexits
|    Bash scripting:
|       http://tldp.org/LDP/abs/html/exitcodes.html
|
*/
defined('EXIT_SUCCESS') or define('EXIT_SUCCESS', 0); // no errors
defined('EXIT_ERROR') or define('EXIT_ERROR', 1); // generic error
defined('EXIT_CONFIG') or define('EXIT_CONFIG', 3); // configuration error
defined('EXIT_UNKNOWN_FILE') or define('EXIT_UNKNOWN_FILE', 4); // file not found
defined('EXIT_UNKNOWN_CLASS') or define('EXIT_UNKNOWN_CLASS', 5); // unknown class
defined('EXIT_UNKNOWN_METHOD') or define('EXIT_UNKNOWN_METHOD', 6); // unknown class member
defined('EXIT_USER_INPUT') or define('EXIT_USER_INPUT', 7); // invalid user input
defined('EXIT_DATABASE') or define('EXIT_DATABASE', 8); // database error
defined('EXIT__AUTO_MIN') or define('EXIT__AUTO_MIN', 9); // lowest automatically-assigned error code
defined('EXIT__AUTO_MAX') or define('EXIT__AUTO_MAX', 125); // highest automatically-assigned error code


/*---------Site Settings--------*/
/*------------------------------*/

/*Site Related Settings*/
define('SITE_NAME', 'Megastar11');
define('SITE_CONTACT_EMAIL', 'support@megastar11.com');
define('MULTISESSION', true);
define('PHONE_NO_VERIFICATION', true);
define('DATE_FORMAT', "%Y-%m-%d %H:%i:%s"); /* dd-mm-yyyy */
define('SPORTS_FILE_PATH', FCPATH . 'uploads/sports.txt');
define('FOOTBALl_SPORTS_FILE_PATH', FCPATH . 'uploads/football_sports.txt');
define('SPORTS_API_NAME', 'CRICKETAPI');
define('FOOTBALL_SPORT_API_NAME', 'CRICKETAPI');

define('DEFAULT_SOURCE_ID', 1);
define('DEFAULT_DEVICE_TYPE_ID', 1);
define('DEFAULT_CURRENCY', 'Rs.');
define('DEFAULT_CURRENCY_CODE', 'INR');
define('REFERRAL_SIGNUP_BONUS', 50);
define('DEFAULT_PLAYER_CREDITS', 6.5);
define('DEFAULT_TIMEZONE', '+05:30');
define('TIMEZONE_DIFF_IN_SECONDS', 7200);
define('IS_VICECAPTAIN', true);
define('MATCH_TIME_IN_HOUR', 300);

define('PAGESIZE_MAX', 105);
define('PAGESIZE_DEFAULT', 15);

define('CompanyName', 'MUDURU Pvt Ltd');
define('Year', 2020);

/* for push Notification */
define('CHANNEL_NAME', 'Alert');
define('ANDROID_SERVER_KEY', 'AAAAqU_5YNo:APA91bGbMo7E-hyyBcfSlGUxW-GnuPbB1NDFjULg4Ep5Dg0eL5oB7rir_wo0CKRADeXIKyLFTksIk2PzvA9FCp8gzNQv7TU1hfg839G-N8I4WoN0awPlyOOC7yMvCD7Z-m_OFjYRjoBX');

/*Social */
define('FACEBOOK_URL', 'https://www.facebook.com/SportsDemo-586849475330688/');
define('TWITTER_URL', 'https://twitter.com/SportsDemo');
// define('LINKEDIN_URL', 'https://www.linkedin.com/company/fsl11');
define('INSTAGRAM_URL', 'https://www.instagram.com/SportsDemo/');
define('YOUTUBE_URL', 'https://www.youtube.com/channel/UClzq2yrAQnQotdFFflPIRfA');

/* Entity Sports API Details */
define('SPORTS_API_URL_ENTITY', 'https://rest.entitysport.com');
define('SPORTS_API_ACCESS_KEY_ENTITY', 'f55cda5a0bc1ccf1badec616db39a827');
define('SPORTS_API_SECRET_KEY_ENTITY', 'a403e586ba951da7a18ab1778f8068a3');

/* Cricket API Sports API Details */
// define('SPORTS_API_URL_CRICKETAPI', 'https://rest.cricketapi.com');
// define('SPORTS_API_ACCESS_KEY_CRICKETAPI', '2e1ea489eb700576032477ba907373f5');
// define('SPORTS_API_SECRET_KEY_CRICKETAPI', 'f3c32e9d76cab75c10346b5598ca1426');
// define('SPORTS_API_APP_ID_CRICKETAPI', 'MWB.SPORTS');
// define('SPORTS_API_DEVICE_ID_CRICKETAPI', 'MWB');
// define('SPORTS_API_URL_CRICKETAPI', 'https://rest.cricketapi.com');
// define('SPORTS_API_ACCESS_KEY_CRICKETAPI', 'a98ba3e4bb214fee3089697b5e71e230');
// define('SPORTS_API_SECRET_KEY_CRICKETAPI', 'f112a553665477f1c75016b6ec9197f4');
// define('SPORTS_API_APP_ID_CRICKETAPI', 'Cric91 App');
// define('SPORTS_API_DEVICE_ID_CRICKETAPI', 'FSL11');
define('SPORTS_API_URL_CRICKETAPI', 'https://rest.cricketapi.com');
define('SPORTS_API_ACCESS_KEY_CRICKETAPI', 'cacb6ebbad4ec176807c30e5f83d454b');
define('SPORTS_API_SECRET_KEY_CRICKETAPI', '50b34453229bf3eaf1d017ac08801157');
define('SPORTS_API_APP_ID_CRICKETAPI', 'com.megastar11');
define('SPORTS_API_DEVICE_ID_CRICKETAPI', 'developer');

// define('SPORTS_API_URL_CRICKETAPI', 'https://rest.cricketapi.com');
// define('SPORTS_API_ACCESS_KEY_CRICKETAPI', '525f89fe71a69c8727d16de07a6a873c');
// define('SPORTS_API_SECRET_KEY_CRICKETAPI', 'ea66b156f4a5bf8c26ad0200e92724f6');
// define('SPORTS_API_APP_ID_CRICKETAPI', 'Betx91.com');
// define('SPORTS_API_DEVICE_ID_CRICKETAPI', 'Cricket');
// define('SPORTS_API_URL_CRICKETAPI', 'https://rest.cricketapi.com');
// define('SPORTS_API_ACCESS_KEY_CRICKETAPI', 'ee248120ff4c10a813b0a4410bb458dc');
// define('SPORTS_API_SECRET_KEY_CRICKETAPI', '514c487e6b2ac5e04a5b8dfbf03bfd85');
// define('SPORTS_API_APP_ID_CRICKETAPI', 'Fsl11AppCricket');
// define('SPORTS_API_DEVICE_ID_CRICKETAPI', 'Cricket');
// define('SPORTS_API_URL_CRICKETAPI', 'https://rest.cricketapi.com');
// define('SPORTS_API_ACCESS_KEY_CRICKETAPI', 'a82232a7b6a15a21c3919e53f7d940f5');
// define('SPORTS_API_SECRET_KEY_CRICKETAPI', '0632a47b4860c1cf1ce9b64c10f82372');
// define('SPORTS_API_APP_ID_CRICKETAPI', 'FantasySportsDemo');
// define('SPORTS_API_DEVICE_ID_CRICKETAPI', 'FSL11');
/* Cricket API Sports API Details */
// define('FOOTBALL_SPORTS_API_URL_CRICKETAPI', 'https://api.footballapi.com');
// define('FOOTBALL_SPORTS_API_ACCESS_KEY_CRICKETAPI', 'bb9a1e1889d84ab99531f5421d022307');
// define('FOOTBALL_SPORTS_API_SECRET_KEY_CRICKETAPI', '6fde8461caa7d271d0bb98183784965e');
// define('FOOTBALL_SPORTS_API_APP_ID_CRICKETAPI', 'footballDev');
// define('FOOTBALL_SPORTS_API_DEVICE_ID_CRICKETAPI', 'developer');
// define('FOOTBALL_SPORTS_API_URL_CRICKETAPI', 'https://api.footballapi.com');
// define('FOOTBALL_SPORTS_API_ACCESS_KEY_CRICKETAPI', '2c63360bd41fff5c254ec6b2dc7b9e31');
// define('FOOTBALL_SPORTS_API_SECRET_KEY_CRICKETAPI', '076e504a2e9adee9137f0def3dc480cb');
// define('FOOTBALL_SPORTS_API_APP_ID_CRICKETAPI', 'WI Teams');
// define('FOOTBALL_SPORTS_API_DEVICE_ID_CRICKETAPI', 'developer');
define('FOOTBALL_SPORTS_API_URL_CRICKETAPI', 'https://api.footballapi.com');
define('FOOTBALL_SPORTS_API_ACCESS_KEY_CRICKETAPI', '430b6ccb1855a92d54de231a2b92e033');
define('FOOTBALL_SPORTS_API_SECRET_KEY_CRICKETAPI', 'b272585017b42792b03d21fecc33aed6');
define('FOOTBALL_SPORTS_API_APP_ID_CRICKETAPI', 'FOOTBALLFANTASY');
define('FOOTBALL_SPORTS_API_DEVICE_ID_CRICKETAPI', 'Football');

/* PayUMoney Details */
define('PAYUMONEY_MERCHANT_KEY', '***');
define('PAYUMONEY_MERCHANT_ID', '6487911');
define('PAYUMONEY_SALT', 'qC5Ssp5Osb');

/* SMS API Details */
define('SMS_API_URL', 'https://login.bulksmsgateway.in/sendmessage.php');
define('SMS_API_USERNAME', 'fonty');
define('SMS_API_PASSWORD', 'Concept@123');
define('SMS_API_SENDER', 'INFOIN');

/* SENDINBLUE SMS API Details */
define('SENDINBLUE_SMS_API_URL', 'https://api.sendinblue.com/v3/transactionalSMS/sms');
define('SENDINBLUE_SMS_SENDER', 'EXACT11');
define('SENDINBLUE_SMS_API_KEY', 'xkeysib-******-72qcrDmbQ0HpGExS');

// TWILIO SMS API Details /
define('TWILIO_AUTH_TOKEN', '1d88cf7dc89cf9b34fb767f8888da799');
define('TWILIO_ACCOUNT_SID', 'AC40e264a612f8e125ef96e73d67f5608c');
define('TWILIO_FROM_NO', '+12172921663'); // SMS Send From

/* MSG91 SMS API Details */
define('MSG91_AUTH_KEY', '342524A45jDN5mi5f6aeb90P1');
define('MSG91_SENDER_ID', 'MYSKLE');
define('MSG91_FROM_EMAIL', 'info@SportsDemo.com');

/* MSG91 SMS FLOW IDs */
define('VERIFY_OTP_ID', '60461510b078c311000b6bcd');
define('LOGIN_OTP_ID', '604614d5c945534bf352ad4c');
define('RESET_PASSWORD_OTP_ID', '600a6af4dea44a76472b0230');
define('ADMIN_ADD_VALUE_OTP_ID', '600a6c2a83a7dd302c2cfada');
define('SHARE_APP_LINK', '606eeccbe678ab3ed73ad805');


switch (ENVIRONMENT)
{
    case 'local':
        /*Paths*/
        define('SITE_HOST', 'http://localhost/');
        define('ROOT_FOLDER', 'SportsDemo/');

        /*SMTP Settings*/
        define('SMTP_PROTOCOL', 'smtp');
        define('SMTP_HOST', 'smtp.gmail.com');
        define('SMTP_PORT', '587');
        define('SMTP_USER', '');
        define('SMTP_PASS', '');
        define('SMTP_CRYPTO', 'tls'); /*ssl*/

        /*From Email Settings*/
        define('FROM_EMAIL', 'info@expertteam.in');
        define('FROM_EMAIL_NAME', SITE_NAME);

        /*No-Reply Email Settings*/
        define('NOREPLY_EMAIL', SITE_NAME);
        define('NOREPLY_NAME', "info@expertteam.in");

        /*Site Related Settings*/
        define('API_SAVE_LOG', false);
        define('CRON_SAVE_LOG', true);

        /* Paytm Details */
        // define('PAYTM_MERCHANT_ID', 'Pfytge92537984428170');
        // define('PAYTM_MERCHANT_KEY', 'PvlUslpF7655u%eV');
        // define('PAYTM_DOMAIN', 'securegw-stage.paytm.in');
        // define('PAYTM_INDUSTRY_TYPE_ID', 'Retail');
        // define('PAYTM_WEBSITE_WEB', 'WEBSTAGING');
        // define('PAYTM_WEBSITE_APP', 'APPSTAGING');
        // define('PAYTM_TXN_URL','https://' . PAYTM_DOMAIN . '/theia/processTransaction');
        // define('PAYUMONEY_ACTION_KEY','https://test.payu.in/_payment');
        /* Razorpay Details */
        define('RAZORPAY_KEY_ID', 'rzp_test_SUNoNjqMPED5hs');
        define('RAZORPAY_KEY_SECRET', 'RmvuxsaFGDQzNYiwAXoNODOx');

        // /* CashFree Details */
        // define('CASHFREE_URL', 'https://test.cashfree.com/');
        // define('CASHFREE_APP_ID', '4202cce7364682b1e84ec8682024');
        // define('CASHFREE_SECRET_KEY', '643b7c8db0c91cabae8210eb8c36c137ef07dd3c');
        
    break;
    case 'testing':

        /*Paths*/
        define('SITE_HOST', 'http://159.65.147.114/');
        define('ROOT_FOLDER', 'SportsDemo/');

        /*SMTP Settings*/
        define('SMTP_PROTOCOL', 'smtp');
        define('SMTP_HOST', 'smtp.gmail.com');
        define('SMTP_PORT', '587');
        define('SMTP_USER', '');
        define('SMTP_PASS', '');
        define('SMTP_CRYPTO', 'tls'); /*ssl*/

        /*From Email Settings*/
        define('FROM_EMAIL', 'info@expertteam.in');
        define('FROM_EMAIL_NAME', SITE_NAME);

        /*No-Reply Email Settings*/
        define('NOREPLY_EMAIL', SITE_NAME);
        define('NOREPLY_NAME', "info@expertteam.in");

        /*Site Related Settings*/
        define('API_SAVE_LOG', false);
        define('CRON_SAVE_LOG', true);

        /* Paytm Details */
        define('PAYTM_MERCHANT_ID', 'xcvuZD47350330583496');
        define('PAYTM_MERCHANT_KEY', 'Lg8U0b08kWKG0Rz0');
        define('PAYTM_DOMAIN', 'securegw-stage.paytm.in');
        define('PAYTM_INDUSTRY_TYPE_ID', 'Retail');
        define('PAYTM_WEBSITE_WEB', 'WEBSTAGING');
        define('PAYTM_WEBSITE_APP', 'APPSTAGING');
        // define('PAYTM_TXN_URL','https://' . PAYTM_DOMAIN . '/theia/processTransaction');
        define('PAYTM_TXN_URL','https://' . PAYTM_DOMAIN . '/order/process');

        /* Razorpay Details */
        define('RAZORPAY_KEY_ID', 'rzp_test_C7fwPdBzgCCfPJ');
        define('RAZORPAY_KEY_SECRET', 'dLIQtRHs8JnsXLNktkOhHOtY');

        /* CashFree Details */
        define('CASHFREE_URL', 'https://test.cashfree.com/');
        define('CASHFREE_APP_ID', '29143ce51c50eb5af1d6bfdc634192');
        define('CASHFREE_SECRET_KEY', 'acabdfcaa3478fea6636d66142b9a6ea769a7bca');

        /* PhonePe Details */
        define('PHONEPE_MERCHANT_ID', 'SportsDemoUAT');
        define('PHONEPE_SALT_KEY', '8289e078-be0b-484d-ae60-052f117f8deb');
        define('PHONEPE_SALT_INDEX', 1);
        define('PHONEPE_END_POINT', "/v4/debit");
        define('PHONEPE_HOST_URL', "https://mercury-uat.phonepe.com");
        define('PHONEPE_CURL_URL', PHONEPE_HOST_URL.PHONEPE_END_POINT);
    break;
    case 'demo':
        /*Paths*/
        define('SITE_HOST', 'http://206.189.148.56/');
        define('ROOT_FOLDER', '');

        /*SMTP Settings*/
        define('SMTP_PROTOCOL', 'smtp');
        define('SMTP_HOST', 'smtp.gmail.com');
        define('SMTP_PORT', '587');
        define('SMTP_USER', '');
        define('SMTP_PASS', '');
        define('SMTP_CRYPTO', 'tls'); /*ssl*/

        /*From Email Settings*/
        define('FROM_EMAIL', 'info@expertteam.in');
        define('FROM_EMAIL_NAME', SITE_NAME);

        /*No-Reply Email Settings*/
        define('NOREPLY_EMAIL', SITE_NAME);
        define('NOREPLY_NAME', "info@expertteam.in");

        /*Site Related Settings*/
        define('API_SAVE_LOG', true);
        define('CRON_SAVE_LOG', true);
        
        /* Razorpay Details */
        define('RAZORPAY_KEY_ID', 'rzp_test_SUNoNjqMPED5hs');
        define('RAZORPAY_KEY_SECRET', 'RmvuxsaFGDQzNYiwAXoNODOx');

        /* CashFree Details */
        // define('CASHFREE_URL', 'https://test.cashfree.com/');
        // define('CASHFREE_APP_ID', '29143ce51c50eb5af1d6bfdc634192');
        // define('CASHFREE_SECRET_KEY', 'acabdfcaa3478fea6636d66142b9a6ea769a7bca');
        
    break;
    case 'production':
        /*Paths*/
        define('SITE_HOST', 'http://megastar11.com/');
        define('ROOT_FOLDER', '');

        /*SMTP Settings*/
        define('SMTP_PROTOCOL', 'smtp');
        define('SMTP_HOST', 'smtp.gmail.com');
        define('SMTP_PORT', '587');
        define('SMTP_USER', '');
        define('SMTP_PASS', '');
        define('SMTP_CRYPTO', 'tls'); /*ssl*/

        /*From Email Settings*/
        define('FROM_EMAIL', 'info@expertteam.in');
        define('FROM_EMAIL_NAME', SITE_NAME);

        /*No-Reply Email Settings*/
        define('NOREPLY_EMAIL', SITE_NAME);
        define('NOREPLY_NAME', "info@expertteam.in");

        /*Site Related Settings*/
        define('API_SAVE_LOG', false);
        define('CRON_SAVE_LOG', true);

        /* Paytm Details */
        define('PAYTM_MERCHANT_ID', '**');
        define('PAYTM_MERCHANT_KEY', '*****');
        define('PAYTM_DOMAIN', 'securegw.paytm.in');
        define('PAYTM_INDUSTRY_TYPE_ID', 'Retail');
        define('PAYTM_WEBSITE_WEB', 'DEFAULT');
        define('PAYTM_WEBSITE_APP', 'DEFAULT');
        // define('PAYTM_TXN_URL','https://' . PAYTM_DOMAIN . '/theia/processTransaction');
        define('PAYTM_TXN_URL','https://'. PAYTM_DOMAIN .'/order/process');

        /* Razorpay Details */
        define('RAZORPAY_KEY_ID', '*****');
        define('RAZORPAY_KEY_SECRET', '**');

        /* CashFree Details */
        define('CASHFREE_URL', 'https://api.cashfree.com/');
        define('CASHFREE_APP_ID', '***');
        define('CASHFREE_SECRET_KEY', '***');
        
        /* PhonePe Details */
        define('PHONEPE_MERCHANT_ID', '****');
        define('PHONEPE_SALT_KEY', '******');
        define('PHONEPE_SALT_INDEX', 2);
        define('PHONEPE_END_POINT', "/v4/debit");
        define('PHONEPE_HOST_URL', "https://mercury-t2.phonepe.com");
        define('PHONEPE_CURL_URL', PHONEPE_HOST_URL.PHONEPE_END_POINT);
    break;
}

define('BASE_URL', SITE_HOST . ROOT_FOLDER . 'api/');
define('ASSET_BASE_URL', BASE_URL . 'asset/');
define('PROFILE_PICTURE_URL', BASE_URL . 'uploads/profile/picture');
define('POST_PICTURE_URL', BASE_URL . 'uploads/Post/');


define('JOIN_CONTEST_TEXT_INVOICE', 'd-402bc1e228c6402aaabc28d4ded5e976');
define('OFFER_NOTIFICATION', 'd-c7dddfea42a641788817ca02e6b65cf6');