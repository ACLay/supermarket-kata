# Supermarket pricing exercise

Model a supermarket pricing calculator inspired by
[PragDave’s Supermarket Kata](http://codekata.com/kata/kata01-supermarket-pricing/).

This currently generates receipt items for a basket of goods along with discounts.
It's laid out as a set of java classes, with junit tests to verify the model.

To run tests and verify the model run `mvn test`

Requires java 17 and maven.

## Design decisions

- Since I/O isn't relevant to the exercise all input to the model is provided via hardcoded values in junit tests
- Rather than deal with prices as a decimal/double I just went with integers to represent pennies. For most shops this would be fine, and we could format it nicely in a separate I/O.
- I initially tried modelling everything in the basket as an item with a quantity. This would have allowed for adding a unit-ed item like Oranges multiple times and just have the one receipt entry, but became problematic when calculating discounts, and would have required more complex logic to ensure unitless items weren't added in non-integer quantities. Eventually I just replaced the Map based model with just a list of items. 
- When applying discounts the system just looks for the first one that could apply. With the samples I've used this should always produce the best result, but if multiple discounts were available for items, or ales in the set had different prices there would exist cases where a better price exists
  - Though what do we mean by "better price"? I'd hope lowest to avoid overcharging customers, but for some less upstanding companies it could be highest. Would depend on how the cost of dealing with complaints compares with the extra revenue...
- I've also assumed that the offers will always be better than the base price, but that may not necessarily be the case. We could leave that as a load time validation step in the I/O, but it would be a good idea to check here too.

## Potential further developments

- Search for the best offers. The model currently just looks for the first offer that could apply to the basket, and picks out the first items it accepts, but what if there's a better combination? It would be more complex and inevitably slower, but it should be possible to do a depth first search of options for what discounts to apply and what to apply them to.
- Optimise the data structure of Items and Discounts. Currently, Offers and Discounts are processed by looping over all discounts in `Basket.getReceipt()`, and then all items in the Offer implementation's `getDiscount()` resulting in a runtime complexity that increases linearly with both basket size and total number of discounts. If we instead load them into a better linked data structure we could reduce it to grow with the average number of discounts per item.
