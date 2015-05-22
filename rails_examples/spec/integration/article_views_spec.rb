require 'spec_helper'
require 'capybara/rspec'

describe "the articles interface" do
  before(:each) do
    @articles = []
    3.times{ @articles << Fabricate(:article) }
  end

  describe "on the index page" do
    before(:each) do
      visit articles_path
    end

    it "should show the page title in all caps", js: true do
      expect(page).to have_selector("h1", text: "ALL ARTICLES")
      save_screenshot('/tmp/file1.png')
      save_screenshot('/tmp/file2.png', :full => true)
    end

    it "should list the article titles" do
      @articles.each do |article|
        expect(page).to have_content(article.title)
      end
    end

    it "should have links for the articles on the index" do
      @articles.each do |article|
        expect(page).to have_link(article.title, href: article_path(article))
      end
    end
  end
end
