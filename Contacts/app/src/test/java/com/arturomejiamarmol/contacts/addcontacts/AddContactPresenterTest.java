package com.arturomejiamarmol.contacts.addcontacts;

import com.arturomejiamarmol.contacts.data.ContactsRepository;
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
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

/**
 * Created by Arturo Mejia on 9/25/17.
 */

public class AddContactPresenterTest {

    @Mock
    ContactsRepository mockRepository;

    @Mock
    AddContactContract.View mockView;

    @Captor
    ArgumentCaptor<Function1<Boolean, Unit>> updateContactArgumentCaptor;


    AddContactPresenter presenter;

    List<Contact> CONTACTS;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new AddContactPresenter(mockView, mockRepository);
        CONTACTS = Arrays.asList(new Contact[]{new Contact(1, "Arturo", "Mejia", "02-09-1990", new ArrayList<Address>(), new ArrayList<Email>(), new ArrayList<Phone>())});

    }

    @Test
    public void addContactWithNoEmptyFieldsShouldAddNewItem() {
        final Contact contact = CONTACTS.get(0);

        when(mockView.areEmptyFields()).thenReturn(false);
        when(mockView.getNewContact()).thenReturn(contact);
        presenter.addContact();
        verify(mockView).getNewContact();
        verify(mockRepository).addContact(contact);
        verify(mockView).showMessageContactAdded();
        verify(mockView).closeScreen();
    }

    @Test
    public void addContactWithEmptyFieldsShouldShowErrorMessage() {
        when(mockView.areEmptyFields()).thenReturn(true);
        presenter.addContact();
        verify(mockView).showErrorOnInvalidFields();
    }

    @Test
    public void loadContactForUpdateShouldSetAllFields(){
        final Contact contact = CONTACTS.get(0);
        presenter.loadContactForUpdate(contact);
        verify(mockView).setName(contact.getFirstName());
        verify(mockView).setLastName(contact.getLastName());
        verify(mockView).setDateOfBirth(contact.getBirthDate());
        verify(mockView).setAddresses(contact.getAddresses());
        verify(mockView).setEmails(contact.getEmails());
        verify(mockView).setPhones(contact.getPhones());

    }

    @Test
    public void updateContactWithEmptyFieldsShouldShowErrorMessage(){
        final Contact contact = CONTACTS.get(0);

        when(mockView.areEmptyFields()).thenReturn(true);
        presenter.updateContact();
        verify(mockView).showErrorOnInvalidFields();
    }

    @Test
    public void updateContactWithNotEmptyFieldsShouldAddUpdateContact(){
        final Contact contact = CONTACTS.get(0);


        presenter.loadContactForUpdate(contact);

        when(mockView.areEmptyFields()).thenReturn(false);
        when(mockView.getNewContact()).thenReturn(contact);

        presenter.updateContact();
        verify(mockView).getNewContact();
        verify(mockView).areEmptyFields();
        verify(mockRepository).updateContact(any(Contact.class),any(Contact.class),updateContactArgumentCaptor.capture());
        updateContactArgumentCaptor.getValue().invoke(true);
        verify(mockView).showMessageContactEdited();
        verify(mockView).closeScreen();
    }


}
