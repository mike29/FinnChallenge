# FinnChallenge
This is a simple android app that gets published products for sale from Finn.no.
It display the products, users can save their favorite item/product.
User can access their favorite produt offline,
Get notfication when not connected,
Switch between fragments that holds favorite and all products. 
<br />
>>If I had more time
I would notify users for all errors exceptions.
Clean someof the methods so it don't mix different tasks in one method, 
one example can be the loop that cleans duplications inside the method that reads from internal storage.
Research a bit more and fix the bug or improve the fragment implementation.
All heavy tasks like getting data should be done on background, using background tread.
Additional features that may improve UX:<br />
Apply cooler effect when like is taped.<br />
Sort and order.<br />
Search by category or location based search (calculate distance from the user using GPS).<br />

>> BUG:<br />
Favorite products are not consistent when displayed togther with new 
products. This might be the way I used fragments. 

