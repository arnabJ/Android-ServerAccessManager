====================
ServerAccessManager
====================
This library is meant to ease the process of sending a request to your server and getting back the response on your Android app. It allows sending a request in 3 different ways.

Features
========
* Supports http get method
* Supports more secure http post method
* Supports sending Files to the server by making use of MultiPartBody
* Exception handling present along with log inputs for developers to fix issues while using the library in their application
* Fully written in Kotlin

To-Do list
==========
* Adding a built-in method to convert json string into ArrayList for easier usage.

Credits
=======
ServerAccessManager a.k.a. SAM makes use of okhttp(https://github.com/square/okhttp) and okio(https://github.com/square/okio) to make network calls to the server.

Requirements
============
* Tested with APIv28, but will work from APIv21 onwards.
* The following permissions are required:
::
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
* For Android versions Pie and above support for http urls is disabled by default by Google. You must either use https urls or else explicitly allow CLEARTEXT in your application. You can do so in several ways.
-> Follow this guide to add support for CLEARTEXT Urls in your application: http://www.douevencode.com/articles/2018-07/cleartext-communication-not-permitted/
-> You may also add this line 'android:usesCleartextTraffic="true"' (without single-quotes) to your application tag in your AndroidManifest.xml file to allow CLEARTEXT Urls such as:
::
    ...
    <application
        android:usesCleartextTraffic="true"
        android:allowBackup="true"
        ...>
        ...
    </application>

Installation
============

Add "maven { url 'https://jitpack.io' }" (without quotes) to your root build.gradle file like this:
::
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

In your applicationModule(app)/build.gradle add the SAM library as a dependency:
::
  dependencies {
    ...
    implementation 'com.github.arnabJ:Android-ServerAccessManager:1.0.0'
  }

Sync project, clean and re-build. You can use the SAM library as part of your project now.

Usage
=====

Kotlin
------
Import SAM:
::
    import tk.arnabjportfolio.sam.*

(Please note, you may also import a particular class by replacing the * with the exact class name in the above import statement.)

Put the below lines of code in your class where you would want to make server call:

For using the http GET method with pre-built url:
::
    /*
     * You need to create an object of the Interface - AsyncResponse after
     * completing it's abstract method and pass the object to the Async class.
     * The response from the server (or a pre-defined error message if any error
     * occurs) will be passed to the method - processFinish(...)
     * You can write your logic here to work with the response string.
     * For example, you may do something like:
     * when (response) {
     *      FailureMessage.ArraysSizeMismatch.msg -> {
     *          outputTxt.text = "Arrays size do not match."
     *      }
     *      FailureMessage.RequestFailed.msg -> {
     *          outputTxt.text = "Request failed. Please try again."
     *      }
     *      else -> {
     *          outputTxt.text = response
     *      }
     * }
     */
    val asyncResponse = object : AsyncResponse {
        override fun processFinish(response: String) {
            // Your code here for whatever you want to do with the response.
            printOutput(response)
        }
    }

    /*
     * Your server side script url with all the name-value pair added.
     */
    val url = "http://www.arnabjportfolio.tk/example_get.php?num1=5&num2=10"

    /*
     * Create an object of the AsyncGet class and pass the url and the Interface
     object and then call obj.execute() to start the server call.
     */
    val task = AsyncGet(url, asyncResponse)
    task.execute()

For using the http GET method without pre-built url:
::
    /*
     * This is same as the above example. It is compulsory.
     */
    val asyncResponse = object : AsyncResponse {
        override fun processFinish(response: String) {
            // Your code here for whatever you want to do with the response.
            printOutput(response)
        }
    }

    /*
     * Make two String Arrays one each for the names and values.
     * The Async class will auto generate the url with all the
     * name-values pairs from the arrays. Please make sure that,
     * the attributes and their values are in same order in the arrays.
     * For example, the below arrays define:
     * https://www.xyz.com?num1=5&num2=6
     */
    val attributes = arrayOf("num1", "num2")
    val values = arrayOf("5", "6")

    /*
     * Your server side script url.
     */
    val url = "http://www.arnabjportfolio.tk/example_get.php"

    /*
     * Create an object of the AsyncGet class and pass the two String arrays,
     the url and the Interface object and then call obj.execute() to start the server call.
     */
    val task = AsyncGet(attributes, values, url, asyncResponse)
    task.execute()

For using the more secure http POST method:
::
    /*
     * This is same as the above example. It is compulsory.
     */
    val asyncResponse = object : AsyncResponse {
        override fun processFinish(response: String) {
            // Your code here for whatever you want to do with the response.
            printOutput(response)
        }
    }

    /*
     * Make two String Arrays one each for the names and values.
     * Please make sure that, the attributes and their values are
     * in same order in the arrays. For example, the below arrays define:
     * num1 = 5, num2 = 6
     */
    val attributes = arrayOf("num1", "num2")
    val values = arrayOf("5", "6")

    /*
     * Your server side script url.
     */
    val url = "http://www.arnabjportfolio.tk/example_post.php"

    /*
     * Create an object of the AsyncPost class and pass the two String arrays,
     the url and the Interface object and then call obj.execute() to start the server call.
     */
    val task = AsyncPost(attributes, values, url, asyncResponse)
    task.execute()

For using the more secure http POST method to send Files along with other data:
::
    /*
     * This is same as the above example. It is compulsory.
     */
    val asyncResponse = object : AsyncResponse {
        override fun processFinish(response: String) {
            // Your code here for whatever you want to do with the response.
            printOutput(response)
        }
    }

    /*
     * Make four String Arrays - one each for:
     * The field names for normal data,
     * The values for the above field names,
     * The field names for the files,
     * The file paths for the above fields.
     * Please make sure that, the attributes and their values are
     * in same order in the arrays. For example, the below arrays define:
     * num1 = 5, num2 = 6, file = /storage/emulated/0/Pictures/hello.jpg
     */
    val attributes = arrayOf("num1", "num2")
    val values = arrayOf("5", "6")
    val fileAttributes = arrayOf("file")
    val fileValues = arrayOf("/storage/emulated/0/Pictures/hello.jpg")

    /*
     * Your server side script url.
     */
    val url = "http://www.arnabjportfolio.tk/example_post_multipart.php"

    /*
     * Create an object of the AsyncMultiPartPost class and pass the four String arrays,
     the url and the Interface object and then call obj.execute() to start the server call.
     */
    val task = AsyncMultiPartPost(attributes, values, fileAttributes, fileValues, url, asyncResponse)
    task.execute()

Credits
=======

* Square [https://github.com/square] for their okhttp & okio libraries
* attenzione [Github user] for his ColorPickerPreference README.rst. Used it as base for this README.rst
