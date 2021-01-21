# PixabayViewer
Load and browse images from Pixabay

# Login
1) Check if e-mail is registered
2) Check password
3) Each user has it's own images (unique id in DB)

# Register
1) Check if e-mail is available (not used)
2) Verify e-mail using RegExp ^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$
3) Verify password (only digits #0 - #9, 6 - 12 digits length)
4) Age from 18 to 99

# Home
1) Load images for current user (from random page for my API_KEY)
2) Check internet connection. When network is available try to load again

# Image details
1) Use Glide to cache images
2) Display image placeholder if image could not be loaded

# Error toasts:
   ## Registration
- This email is already registered!
- Error occurred during account creation

   ## Login
- This email is not registered!
- Wrong password!
- Error occurred during login

  ## Network
  - No internet connection!
  - Can not load images!
  
  # Info toast
  - Account created: example@mail.com

