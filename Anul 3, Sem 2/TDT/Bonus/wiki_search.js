describe('Search Wikipedia', () => {
    it('Visit Wikipedia page', () => {
      cy.visit('https://www.pbinfo.ro/')
      cy.get('[id^=user]').type('razvannasca {enter}')
    })
  })