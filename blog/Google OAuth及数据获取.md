Google OAuth 及 数据获取
----------

我们的程序要从Google获取一些用户的信息，就需要通过Google OAuth认证

####1. 注册Client ID & SECRET 

1. 去 [Google Developers Console](https://console.developers.google.com/) 注册
2. Create a Project
3. 在新Project的 Credentials 中可以找到 Client ID 和 Client secret，后面要用 
4. 在 Authorized redirect URIs 中添加 https://developers.google.com/oauthplayground 以方便调试
5. 在 Authorized redirect URIs 中再添加 自己项目的 callback URL，如果是本机就添加 http://localhost...

####2. 调试 OAuth

可以在 [Google Developers](https://developers.google.com/oauthplayground/) 上调试OAuth 

1. 点击 OAuth 2.0 configuration (小齿轮icon) 按钮
2. 选中 Use your own OAuth credentials 
3. 填入自己的 OAuth Client ID 
4. 填入自己的 OAuth Client secret，设置完成

######Step 1: Select & authorize APIs

这里可以选择多个需要访问的scope，可以理解为你要访问Google的哪些服务, 我们可以选中 

+ Google OAuth2 API v2 下的 https://www.googleapis.com/auth/userinfo.email 
+ 和 Webmaster Tools API v3 中的 https://www.googleapis.com/auth/webmasters.readonly

> OAuth不会返回用户的gmail地址，需要https://www.googleapis.com/auth/userinfo.email 来获取其Gmail地址

点击 Authorize APIs 按钮，就会生成请求Google OAuth的URL地址，并直接使用在浏览器中，登录自己的Gmail账号，并Allow，就会再次回到刚才的 Google Developers页面，并进入 Step 2

######Step 2: Exchange authorization code for tokens

这个页面里，能看到刚才请求Google OAuth的连接 

		https://accounts.google.com/o/oauth2/auth?redirect_uri=你的CallbackURL地址&response_type=code&client_id=你的ClientID账号&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fwebmasters.readonly&approval_prompt=force&access_type=offline

能看到scope中有刚才选中的2个scope 

		GET /oauthplayground/?code=4/3H_cac17h80xDD0Fg

Google会返回一个code编码，我们用这个编码在 Step 2 中获取 access_token 和 refresh_token ， 点击Exchange authorization code for tokens 按钮，并进入 Step 3

> The access token will expire in 3600 seconds.
> 需要用 refresh token 来获取新的 access token

######Step 3: Configure request to API

在 Request URI中填入想要请求的URL地址，就可以取到数据了。 比如填写

		https://www.googleapis.com/oauth2/v1/userinfo?alt=json

并点击 Send the request按钮，就可以得到返回的用户Gmail信息了

		{
		"family_name": "", 
		"name": "", 
		"picture": "https://lh3.googleusercontent.com/-XXX5M/photo.jpg", 
		"email": "abc@gmail.com", 
		"given_name": "", 
		"id": "111111112222222222", 
		"hd": "", 
		 "verified_email": true
		}

####3. Google OAuth Java API 

######Maven 配置

		<dependency>
			<groupId>com.google.api-client</groupId>
			<artifactId>google-api-client</artifactId>
			<version>1.20.0</version>
		</dependency>

		<dependency>
			<groupId>com.google.oauth-client</groupId>
			<artifactId>google-oauth-client-jetty</artifactId>
			<version>1.20.0</version>
		</dependency>