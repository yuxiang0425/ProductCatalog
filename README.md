Product Catalog App

How to run:
1. Download and unzip the whole file
2. Open the unzipped file in Android Studio
3. Wait for Gradle sync
4. It's ready to run on an emulator with API 37+ only. Internet connection is required

Stack:
- Kotlin + Jetpack compose for User Interface
- Retrofit + Gson for connecting to dummyjson.com
- Coil(AsyncImage) for image loading
- ViewModel + StateFlow for state management

Architecture:
Project is slipt into 2 layers
/data - network and model layer
/ui - presentation layer
* Pagination
  tracks currentSkip and API total to know if there is anything left to load.
  tracks scroll position and call loadMoreProducts() once user is almost reach the end.
  isFetching flag prevent duplicate or overlapping caused by parallel fetches trigger during rapid scroll events.
* State
  Loading(initial load only, pagination uses footer instead to avoid screen flash), Empty(when api return null value), Error(exception message), Success(the product list)

TODOs' / What is not finished
1. Product detail page
2. Retry button on Error state
3. Search feature

AI assistance
This is my first kotlin project, I used claude as coding assistant.
1. How to obtain data from dummyjson api (Retrofit + Gson)
2. Most of the coding
3. Coil AsyncImage usage
4. ProductList draft with first version of pagination that has bug
5. Bug fix with dependencies error
