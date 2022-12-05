package telran.java2022.book.dao;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import telran.java2022.book.model.Author;
import telran.java2022.book.model.Book;

@Repository
public class BookRepository {
	
	@PersistenceContext//(type = PersistenceContextType.EXTENDED)
	EntityManager em;
	
	@Transactional
	public void addBooks() {
		Author markTwain = Author.builder()
									.fullName("Mark Twain")
									.build();
		em.persist(markTwain);
		Book pandp = Book.builder()
							.isbn("978-0140350173")
							.title("The Prince and the Pauper")
							.author(markTwain)
							.build();
		em.persist(pandp);
		Author ilf = Author.builder()
							.fullName("Ilya Ilf")
							.build();
		Author petrov = Author.builder()
							.fullName("Yevgeny Petrov")
							.build();
		em.persist(ilf);
		em.persist(petrov);
		
		Book chairs12 = Book.builder()
							.isbn("978-0810114845")
							.title("The Twelve Chairs")
							.author(ilf)
							.author(petrov)
							.build();
		em.persist(chairs12);
	}
	
	//@Transactional(readOnly = true)
	public void printAuthorsOfBook(String isbn) {
//		Book book = em.find(Book.class, isbn);
		TypedQuery<Book> query = em.createQuery("select b from Book b left join fetch b.authors a where b.isbn=?1", Book.class);
		query.setParameter(1, isbn);
		Book book = query.getSingleResult();
		Set<Author> authors = book.getAuthors();
		authors.forEach(a -> System.out.println(a.getFullName()));
	}

}