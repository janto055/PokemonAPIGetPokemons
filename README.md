<!-- (This is a comment) INSTRUCTIONS: Go through this page and fill out any **bolded** entries with their correct values.-->

# AND101 Project 5 - Choose Your Own API

Submitted by: **Jaydon Antoine**

Time spent: **5** hours spent in total

## Summary

**Pokemon API Get A Pokemon** is an android app that **the displays a random pokemon pulled from the PokeAPI. At the moment, it displays an image of the back of the character, its name, alongside its weight.**

If I had to describe this project in three (3) emojis, they would be: **🐉🐳🦄**

## Application Features

<!-- (This is a comment) Please be sure to change the [ ] to [x] for any features you completed.  If a feature is not checked [x], you might miss the points for that item! -->

The following REQUIRED features are completed:

- [X] Make an API call to an API of your choice using AsyncHTTPClient
- [X] Display at least three (3) pieces of data for each API entry retrieved
- [X] A working Button requests a new API entry and updates the data displayed

The following STRETCH features are implemented:

- [ ] Add a query to the API request
  - The query I added is **FILL IN HERE**
- [ ] Build a UI to allow users to add that query

The following EXTRA features are implemented:

- [ ] List anything else that you added to improve the app!

## API Choice

My chosen API for this project is **PokeAPI**.

## Video Demo

Here's a video / GIF that demos all of the app's implemented features:

<img src='https://i.imgur.com/RsBljsP.gif' title='Video Demo' width='' alt='Video Demo' />

GIF created with **Imgur**

<!-- Recommended tools:
- [Kap](https://getkap.co/) for macOS
- [ScreenToGif](https://www.screentogif.com/) for Windows
- [peek](https://github.com/phw/peek) for Linux. -->

## Notes

Once again, this project proved to be a very valuable learning experience. In this case, it proved to be difficult to find an API that was easy to use within the AsyncHTTPClient framework provided. 
As a result, I ended up scratching my plans to build a music app, and stuck with one of the recommended APIs, in this case the PokeAPI. 

The main struggle I encountered was being able to parse through the JSON data, specifically for desplaying the image/sprite associated with the randomly chosen pokemon. For some reason, 
the JSON response was formatted in a way that made it very difficult to single out a specific image URL. After a few hours of trying, I ended up settling with just removing leading and trailing 
elements from the pokeImage string so that I would end up with the very first URL of the "sprites" JSON. Moving foward, I will look into better ways to get this feature to work, as I would love 
to have the front facing sprite be displayed instead. 

## License

Copyright **2023** **Jaydon Antoine**

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
