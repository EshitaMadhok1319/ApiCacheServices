Implemented caching service in Java. The caching service have,

The MAX number of elements in memory should be configurable when caching service is initialized. When number of elements in the internal cache exceeds the max, the service should evict the least-used element to database. Service should support the following APIs.

- add(Entity e1) - Adds e1 to internal cache.
- remove(Entity e1) - Removes e1 from the internal cache and database.
- removeAll() - Removes all the Entities from internal cache and database.
- get(Entity e1) - Return an object from the internal cache or from database.
- clear() - Clears the internal cache. No impact entries in database.
