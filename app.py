from flask import Flask, redirect, render_template, url_for, request
from DB_handler import DBModule

app = Flask(__name__)
DB = DBModule()

@app.route("/")
def index():
    pass


if __name__ == "__main__":
    app.run(host="0.0.0.0", debug=True)