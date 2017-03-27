# Shodand
Shodand is a native Android application developed to consume Shodan data. 
It is developed following the principles of MVP architecture. It uses the [jShodan library](https://github.com/fooock/jshodan) (created by me! :smile:) to make the network requests.You can find a complete list of the app dependencies divided by modules in the following file, located at the repository root level:
```bash
buildsystem/libraries.gradle
```
**Important**: Note that this application is under heavy development, 
feel free to contribute! :heart: 
 
### Prerequisites
This application works in Android devices ```15+```
### Permissions
This app uses the following **permissions**:
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.CAMERA" />
```
### Uses feature
The application uses the camera, but **is not required**. This is only required if you want to scan your Shodan API key from the QR code provided in your Shodan account. If you don't want to grant this permission you can copy paste your API key.
```xml
<uses-feature
        android:name="android.hardware.camera"
        android:required="false" />
```

## Structure
The application is divided in three modules:
* **app**: Contains all views and Android classes. This is the only Android module.
* **domain**: This module contains all business logic. This is a Java module
* **data**: Contains all classes and interfaces to transform and retrieve data from different datasources. This is a Java module.

All modules can be tested separately. Also if you want to create a Java console application, you can reuse the **data** and **domain** modules!

## Views
All views need to implement the [BaseView](https://github.com/fooock/shodand/blob/master/app/src/main/java/com/fooock/app/shodand/view/BaseView.java) interface.
```java
public interface FakeView extends BaseView {
    ...
}
```
You can use it in ```Fragment``` or ```Activity```
```java
public class FakeFragment implements FakeView {
    ...
}
```
## Presenters
All presenters must extends the [BasePresenter](https://github.com/fooock/shodand/blob/master/app/src/main/java/com/fooock/app/shodand/presenter/BasePresenter.java) class and pass to it the view type. Imagine you have a view ```FakeView``` that extends the ```BaseView``` interface:
```java
public class FakePresenter extends BasePresenter<FakeView> {
    ....
}
```
You need to attach the presenter to the view. 
```java
public class FakeFragment implements FakeView {
    private FakePresenter presenter;
    
    @Override
    public View onCreateView(...) {
        ....
        presenter.attachView(this);
        ...
        return view;
    }
    
    @Override
    public void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }
}
```

## Interactors
Interactors are use cases. All interactors must extends the ```BaseInteractor``` class. All interactors receive:
* The type of the result
* The interactor params 

Imagine you want to create an use case that returns a list of ```String```s and no need params:
```java
public class FakeInteractor extends BaseInteractor<List<String>, Void> {

    @Override
    protected Observable<List<String>> result(Void params) {
        return ...
    }
}

```
All logic is implemented in the ```result(...)``` method.

## Contributing
Ouuh yeah! Feel free to create issues and send PR's :heart:

## License
```
Copyright 2017 newhouse (nhitbh at gmail dot com)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)


   [dill]: <https://github.com/joemccann/dillinger>
   [git-repo-url]: <https://github.com/joemccann/dillinger.git>
   [john gruber]: <http://daringfireball.net>
   [df1]: <http://daringfireball.net/projects/markdown/>
   [markdown-it]: <https://github.com/markdown-it/markdown-it>
   [Ace Editor]: <http://ace.ajax.org>
   [node.js]: <http://nodejs.org>
   [Twitter Bootstrap]: <http://twitter.github.com/bootstrap/>
   [jQuery]: <http://jquery.com>
   [@tjholowaychuk]: <http://twitter.com/tjholowaychuk>
   [express]: <http://expressjs.com>
   [AngularJS]: <http://angularjs.org>
   [Gulp]: <http://gulpjs.com>

   [PlDb]: <https://github.com/joemccann/dillinger/tree/master/plugins/dropbox/README.md>
   [PlGh]: <https://github.com/joemccann/dillinger/tree/master/plugins/github/README.md>
   [PlGd]: <https://github.com/joemccann/dillinger/tree/master/plugins/googledrive/README.md>
   [PlOd]: <https://github.com/joemccann/dillinger/tree/master/plugins/onedrive/README.md>
   [PlMe]: <https://github.com/joemccann/dillinger/tree/master/plugins/medium/README.md>
   [PlGa]: <https://github.com/RahulHP/dillinger/blob/master/plugins/googleanalytics/README.md>
