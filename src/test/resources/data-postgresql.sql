-- Test data.
-- Used only for `mvn test` phase.

-- =====================================================================================================================
-- Test: InquiryRepositoryIT
-- =====================================================================================================================

-- testFindInquiryByEmailOrPhoneNumber*

INSERT INTO inquiries
VALUES (DEFAULT, 'Test 110', 'test110@example.org', '0900 110 110', null);

INSERT INTO inquiries
VALUES (DEFAULT, 'Test 111', 'test111@example.org', null, null);

INSERT INTO inquiries
VALUES (DEFAULT, 'Test 112', null, '0900 112 112', null);

INSERT INTO inquiries
VALUES (DEFAULT, 'Test 113', 'test113@example.org', '0900 113 113', null);

INSERT INTO inquiries
VALUES (DEFAULT, 'Test 120', 'test120@example.org', null, 'Test inquiry #120.');