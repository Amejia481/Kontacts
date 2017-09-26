package com.arturomejiamarmol.contacts.contacts;

import com.arturomejiamarmol.contacts.data.ContactsRepository;
import com.arturomejiamarmol.contacts.data.OnMemoryContactRepository;
import com.arturomejiamarmol.contacts.data.models.Address;
import com.arturomejiamarmol.contacts.data.models.Contact;
import com.arturomejiamarmol.contacts.data.models.Email;
import com.arturomejiamarmol.contacts.data.models.Phone;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by Arturo Mejia on 9/25/17.
 */

public class ContactsPresenterTest {

    ContactsPresenter presenter;

    @Mock
    ContactsContract.View mockView;

    @Mock
    ContactsRepository mockRepository;

    @Captor
    ArgumentCaptor<Function1<List<Contact>, Unit>> findConctactArgumentCaptor;

    @Captor
    ArgumentCaptor<Function1<List<Contact>, Unit>> loadCallbackArgumentCaptor;

    @Captor
    ArgumentCaptor<Function1<Boolean, Unit>> deleteContactArgumentCaptor;

    List<Contact> CONTACTS;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new ContactsPresenter(mockView, mockRepository);
        CONTACTS = Arrays.asList(new Contact[]{new Contact(1, "Arturo", "Mejia", "02-09-1990", new ArrayList<Address>(), new ArrayList<Email>(), new ArrayList<Phone>())});
    }

    @Test
    public void loadContactsShouldShowContacts() {
        presenter.loadContacts();
        verify(mockRepository).getContacts(loadCallbackArgumentCaptor.capture());
        loadCallbackArgumentCaptor.getValue().invoke(CONTACTS);
        verify(mockView).showContacts(CONTACTS);

    }

    @Test
    public void deleteContactShouldDeleteItem() {
        final Contact contact = CONTACTS.get(0);

        presenter.deleteContact(contact);
        verify(mockRepository).removeContact(any(Contact.class), deleteContactArgumentCaptor.capture());
        deleteContactArgumentCaptor.getValue().invoke(anyBoolean());
        verify(mockView).deleteContact(contact);

    }

    @Test
    public void onSearchContactShouldShouldOneItemWithCorrectPatter() {
        final Contact contact = CONTACTS.get(0);
        presenter.onSearch(contact.getFirstName());
        verify(mockRepository).findContact(anyString(), findConctactArgumentCaptor.capture());
        findConctactArgumentCaptor.getValue().invoke(CONTACTS);
        verify(mockView).showContacts(CONTACTS);

    }

    @Test
    public void onSearchContactWithEmptyValueShouldReturnAllContacts(){
        final Contact contact = CONTACTS.get(0);
        presenter.onSearch(" ");
        verify(mockRepository).getContacts(loadCallbackArgumentCaptor.capture());
        loadCallbackArgumentCaptor.getValue().invoke(CONTACTS);
        verify(mockView).showContacts(CONTACTS);


    }

    @Test
    public void showEditContactScreenShouldShowScreen(){
        final Contact contact = CONTACTS.get(0);
        presenter.showEditContactScreen(contact);
        verify(mockView).showEditContactScreen(contact);



    }
}
