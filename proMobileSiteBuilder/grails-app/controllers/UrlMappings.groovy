class UrlMappings {

	static mappings = {
	   
		// Home Page
		"/" (view: '/index')
		"/**" (view: '/index')

		"/api/auth/login" (controller: "login", action: "login", method: "POST")
		"/api/register" (controller: "registration", action: "register", method: "POST")
		"/api/auth/logout" (controller: "logout", action: "logout", method: "POST")

		"/"(view:"/index")
		"500"(view:'/error')
		"404"(view:'/notFound')
	}
}
