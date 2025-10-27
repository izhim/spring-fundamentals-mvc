# 🌱 Spring Boot Fundamentals & MVC


This section introduces the core concepts of **Spring Boot**, covering both **MVC controllers** for rendering dynamic HTML views with Thymeleaf and **REST controllers** for providing JSON data to clients. It explains how to handle HTTP requests, map routes with annotations, pass data between controllers and views, and manage request parameters.


Key topics covered:
- Understanding the MVC architecture and REST principles in Spring Boot
- Using annotations like `@Controller`, `@RestController`, `@GetMapping`, `@RequestParam`, `@PathVariable`, and `@ModelAttribute`
- Working with models (`Model`, `ModelMap`) and DTOs
- Creating REST endpoints and returning data as DTOs, maps, or lists
- Rendering dynamic views with Thymeleaf templates
- Handling query parameters and HTTP servlet requests
- Managing redirects, forwards, and dynamic content injection

---


# 🧩 Thymeleaf Integration — UserController


Below is a simple example of integrating **Thymeleaf** with a Spring Boot controller, with **comments explaining each method**.


## 📘 Controller






### Method: /details 

Handles GET requests to '/details', creates a single User object and adds it to the model. Also adds a 'title' attribute for the view and returns the view name 'details' to be rendered by Thymeleaf
```java
@GetMapping("/details")
public String details(Model model) {
  User user = new User("Jose", "Carrillo");
  model.addAttribute("title", "Hello Cruel World");
  model.addAttribute("user", user);
  return "details";
}
```

### Method: /list

Handles GET requests to '/list', adds a 'title' attribute to the model. The list of users is injected globally by the @ModelAttribute method and returns the view name 'list'
```java
@GetMapping("/list")
public String list(ModelMap model) {
  model.addAttribute("title", "User List Example");
  return "list";
}
```

### Global Model Attribute
This method runs before each request handler and provides a 'users' list to all views in this controller
```java
@ModelAttribute("users")
public List<User> usersModel() {
  return Arrays.asList(
    new User("Jose", "Carrillo", "carrillo@email.com"),
    new User("Manuel", "Benitez"),
    new User("Paco", "Lolo"));
}
```


### 🔍 Key Concepts
- **`@Controller`** → Marks the class as a Spring MVC controller returning view templates.
- **`Model` / `ModelMap`** → Passes data from backend to the Thymeleaf view.
- **`@ModelAttribute`** → Makes attributes globally available to all views within the controller.
- **Return value** → The view name (without `.html`) under `/templates/`.


## 🏠 Home Controller — Redirect Example

This controller redirects incoming requests (`/`, `/home`, or empty path) to the `/list` route.  
Demonstrates the difference between **redirect** (new request, updates URL) and **forward** (same request, keeps parameters).

```java
@Controller
public class HomeController {

    /* REDIRECT TO LIST REFRESHING DATA */
    @GetMapping({"", "/", "/home"})
    public String home() {
        return "redirect:/list";    // changes the URL path and refreshes
        // return "forward:/list";  -> keeps the same HTTP request
    }
}
```


## 🧠 Thymeleaf Basics


### 🏷️ Binding Variables
Model attributes are accessed using `${}`.


```html
<h1 th:text="${title}"></h1>
<ul>
  <li th:text="${user.name}"></li>
  <li th:text="${user.lastname}"></li>
  <li th:if="${user.email}" th:text="${user.email}"></li>
  <li th:if="${user.email == null}" th:text="${'no tiene email'}"></li>
</ul>
```


### 🔁 Iteration
Loop over lists using `th:each`.


```html
<div th:if="${users.size()!=0}">
  <table>
    <thead>
      <tr><th>name</th><th>lastname</th><th>email</th></tr>
    </thead>
    <tbody>
      <tr th:each="user : ${users}">
        <td th:text="${user.name}"></td>
        <td th:text="${user.lastname}"></td>
        <td th:if="${user.email == null}" th:text="${'No email'}"></td>
        <td th:if="${user.email != null}" th:text="${user.email}"></td>
      </tr>
    </tbody>
  </table>
</div>
```


### ⚙️ Conditional Rendering
Use `th:if` and `th:unless`.


```html
<p th:if="${user.email != null}" th:text="${user.email}"></p>
<p th:unless="${user.email != null}" th:text="'No email available'"></p>
```


## 🔗 Dynamic Links
Demonstrates dynamic URL generation and passing query parameters.
```html
<div>
  <a th:href="@{/list}">Show the list</a><br>
  <a th:href="@{'api/params/foo?message=' + ${title}}">Show message</a><br>
  <a th:href="@{/api/params/foo(message='this is the second message')}">Show second message</a><br>
  <a th:href="@{/api/params/bar(text='this is a text', code=12345)}">Show text and code</a><br>
</div>
```

### 🧩 Common Attributes
| Attribute | Description |
|------------|--------------|
| `th:text` | Replaces the content of an element with evaluated text |
| `th:each` | Iterates over a collection |
| `th:if` / `th:unless` | Conditional rendering |
| `th:href` | Dynamically sets a link’s `href` attribute |
| `th:object` / `th:field` | Bind form data to model objects |


## ✅ Summary
- The **controller** passes data to Thymeleaf views using `Model`.
- **HomeController** demonstrates redirect and forward behaviors for navigation. 
- Views access and display this data dynamically.
- Handles lists, conditions, and dynamic content without JavaScript.


📁 **Example views:**
- `details.html` → Displays a single user's information.
- `list.html` → Renders a table of users with conditional email display.


---


# 🧩 User REST API — UserRestController


Below is an example of a **Spring Boot REST controller** with **comments explaining each method**. It demonstrates returning data as DTOs, maps, and lists using JSON.


## 📘 Controller Example


### Method: /api/details
Handles GET requests to '/api/details'. Creates a single User object, wraps it in a UserDto, sets a title, and returns it as JSON.
```java
@GetMapping(path = "/details")
public UserDto details() {
User user = new User("Jose", "Carrillo");
UserDto userDto = new UserDto();
userDto.setUser(user);
userDto.setTitle("Hola Mundo Cruel");
return userDto;
}
```


### Method: /api/details-map
Handles GET requests to '/api/details-map'. Creates a User object and returns it in a `Map` with a title, serialized as JSON.
```java
@GetMapping(path = "/details-map")
public Map<String, Object> detailsMap() {
User user = new User("Jose", "Carrillo");
Map<String, Object> body = new HashMap<>();
body.put("title", "Hola Mundo Cruel");
body.put("user", user);
return body;
}
```


### Method: /api/list
Handles GET requests to '/api/list'. Returns a list of User objects as JSON.
```java
@GetMapping("/list")
public List<User> list() {
User user1 = new User("Jose", "Carrillo");
User user2 = new User("Manolo", "Jimenez");
User user3 = new User("Maria", "Cabello");


List<User> users = Arrays.asList(user1, user2, user3);
return users;
}
```


### 🔍 Key Concepts
- **`@RestController`** → Marks the class as a REST controller returning JSON.
- **`@RequestMapping`** → Defines a base URL for all endpoints in the controller.
- **`@GetMapping`** → Maps HTTP GET requests to handler methods.
- **DTOs / Maps / Lists** → Different ways to structure data returned by the API.
- Responses are automatically serialized to JSON by Spring Boot.


## ✅ Summary
- The **REST controller** provides data endpoints for clients or frontend applications.
- Data can be returned as DTOs, maps, or lists.
- Simplifies client-server communication using JSON.


📁 **Example endpoints:**
- `/api/details` → Returns a single User with a title in DTO format.
- `/api/details-map` → Returns a single User with a title in a Map.
- `/api/list` → Returns a list of User objects.


---


# 🧩 Path Variables & Environment — `PathVariableController`

Demonstrates the use of **`@PathVariable`**, **`@Value`**, **SpEL**, and **Environment** for configuration-driven API endpoints.

### `/api/var/baz/{message}`
Returns a message from the URL path.
```java
@GetMapping("/baz/{message}")
public ParamDto baz(@PathVariable String message) {
  ParamDto param = new ParamDto();
  param.setMessage(message);
  return param;
}
```

### `/api/var/mix/{product}/{code}`
Returns multiple path variables in a JSON map.
```java
@GetMapping("/mix/{product}/{code}")
public Map<String, Object> mix(@PathVariable String product, @PathVariable Long code) {
  Map<String, Object> json = new HashMap<>();
  json.put("product", product);
  json.put("code", code);
  return json;
}
```

### `/api/var/create` (POST)
Receives a `User` object, modifies it, and returns it.
```java
@PostMapping("/create")
public User create(@RequestBody User user) {
  user.setName(user.getName().toUpperCase());
  user.setLastname(user.getLastname().toUpperCase());
  return user;
}
```

### `/api/var/values`
Returns configuration values from `@Value`, SpEL, and `Environment`.
```java
@GetMapping("/values")
public Map<String, Object> values() {
  Map<String, Object> json = new HashMap<>();
  json.put("username", username);
  json.put("message", message);
  json.put("listOfValues", listOfValues);
  json.put("code", code);
  json.put("valueList", valueList);
  json.put("valueString", valueString);
  json.put("valuesMap", valuesMap);
  json.put("product", product);
  json.put("message2", environment.getProperty("config.message"));
  json.put("code2", environment.getProperty("config.code", Integer.class));
  return json;
}
```

### 🔍 Key Concepts
- **`@PathVariable`** → Extracts values from URL paths.  
- **`@Value`** → Injects properties from application configuration.  
- **SpEL (`#{...}`)** → Enables expressions like lists, maps, or transformations.  
- **`Environment`** → Accesses configuration programmatically.  

## ✅ Summary
- Combines **path variables** and **config-driven values** for flexible endpoints.  
- Demonstrates **JSON mapping**, POST handling, and environment-based configuration.  

📁 **Example Endpoints**
- `/api/var/baz/hello` → Returns message `"hello"`.  
- `/api/var/mix/productA/12345` → Returns `{"product":"productA","code":12345}`.  
- `/api/var/create` → Accepts a `User` object and returns the modified user.  
- `/api/var/values` → Returns all injected configuration values.  

---


# 🧩 Request Parameters — RequestParamController


Below is an example of a **Spring Boot REST controller** handling **HTTP request parameters**, with **comments explaining each method**.


## 📘 Controller Example


### Method: /api/params/foo
Handles GET requests to '/api/params/foo'. Accepts an optional `message` parameter using `@RequestParam`. Returns a `ParamDto` with the provided message or a default message if none is supplied.
```java
@GetMapping("/foo")
public ParamDto foo(@RequestParam(required = false, defaultValue = "mensaje por defecto") String message) {
ParamDto param = new ParamDto();
param.setMessage(message);
return param;
}
```


### Method: /api/params/bar
Handles GET requests to '/api/params/bar'. Accepts multiple parameters (`text` and `code`) using `@RequestParam` and returns them in a `ParamDto`.
```java
@GetMapping("/bar")
public ParamDto bar(@RequestParam String text, @RequestParam Integer code) {
ParamDto params = new ParamDto();
params.setMessage(text);
params.setCode(code);
return params;
}
```


### Method: /api/params/request
Handles GET requests to '/api/params/request'. Retrieves parameters directly from the `HttpServletRequest` object and returns them in a `ParamDto`.
```java
@GetMapping("/request")
public ParamDto request(HttpServletRequest request) {
ParamDto param = new ParamDto();
param.setCode(Integer.parseInt(request.getParameter("code")));
param.setMessage(request.getParameter("message"));
return param;
}
```


### 🔍 Key Concepts
- **`@RequestParam`** → Binds query parameters from the URL to method arguments.
- **Optional parameters** → Can provide a default value if the parameter is missing.
- **Multiple parameters** → Accept multiple `@RequestParam` arguments in the same request.
- **`HttpServletRequest`** → Access request parameters manually if needed.
- Responses are automatically serialized to JSON by Spring Boot.


## ✅ Summary
- The **RequestParam controller** demonstrates how to receive query parameters in a REST API.
- Supports optional parameters, multiple parameters, and manual request handling.
- Returns structured data in `ParamDto` objects.


📁 **Example endpoints:**
- `/api/params/foo?message=hello` → Returns a message or default if omitted.
- `/api/params/bar?text=hello&code=123` → Returns both text and code.
- `/api/params/request?message=hi&code=456` → Retrieves parameters via HttpServletRequest.
