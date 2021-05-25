/**
 *
 */
package home.app.api.configuration;

@FunctionalInterface
public interface JaxRsClientFactory {

	<E> E createClient(String uri, Class<E> clazz);

}
