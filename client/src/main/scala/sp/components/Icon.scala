package sp.components

import japgolly.scalajs.react.vdom.VdomNode
import japgolly.scalajs.react.vdom.html_<^._

/**
 * Provides type-safe access to Font Awesome icons
  * These are from an earlier version of FontAwesome. If they don't work, look up the correct one for FontAwesome 5.
 */
object Icon {
  type Icon = VdomNode
  private def icon(prefix: String, name: String) = <.i(^.className := s"$prefix$name")
  def fas(name: String): Icon = icon("fas fa-", name)

  def adjust = fas("adjust")
  def adn = fas("adn")
  def alignCenter = fas("align-center")
  def alignJustify = fas("align-justify")
  def alignLeft = fas("align-left")
  def alignRight = fas("align-right")
  def ambulance = fas("ambulance")
  def anchor = fas("anchor")
  def android = fas("android")
  def angellist = fas("angellist")
  def angleDoubleDown = fas("angle-double-down")
  def angleDoubleLeft = fas("angle-double-left")
  def angleDoubleRight = fas("angle-double-right")
  def angleDoubleUp = fas("angle-double-up")
  def angleDown = fas("angle-down")
  def angleLeft = fas("angle-left")
  def angleRight = fas("angle-right")
  def angleUp = fas("angle-up")
  def apple = fas("apple")
  def archive = fas("archive")
  def areaChart = fas("area-chart")
  def arrowCircleDown = fas("arrow-circle-down")
  def arrowCircleLeft = fas("arrow-circle-left")
  def arrowCircleODown = fas("arrow-circle-o-down")
  def arrowCircleOLeft = fas("arrow-circle-o-left")
  def arrowCircleORight = fas("arrow-circle-o-right")
  def arrowCircleOUp = fas("arrow-circle-o-up")
  def arrowCircleRight = fas("arrow-circle-right")
  def arrowCircleUp = fas("arrow-circle-up")
  def arrowDown = fas("arrow-down")
  def arrowLeft = fas("arrow-left")
  def arrowRight = fas("arrow-right")
  def arrowUp = fas("arrow-up")
  def arrows = fas("arrows")
  def arrowsAlt = fas("arrows-alt")
  def arrowsH = fas("arrows-h")
  def arrowsV = fas("arrows-v")
  def asterisk = fas("asterisk")
  def at = fas("at")
  def automobile = fas("automobile")
  def backward = fas("backward")
  def ban = fas("ban")
  def bank = fas("bank")
  def barChart = fas("bar-chart")
  def barChartO = fas("bar-chart-o")
  def barcode = fas("barcode")
  def bars = fas("bars")
  def bed = fas("bed")
  def beer = fas("beer")
  def behance = fas("behance")
  def behanceSquare = fas("behance-square")
  def bell = fas("bell")
  def bellO = fas("bell-o")
  def bellSlash = fas("bell-slash")
  def bellSlashO = fas("bell-slash-o")
  def bicycle = fas("bicycle")
  def binoculars = fas("binoculars")
  def birthdayCake = fas("birthday-cake")
  def bitbucket = fas("bitbucket")
  def bitbucketSquare = fas("bitbucket-square")
  def bitcoin = fas("bitcoin")
  def bold = fas("bold")
  def bolt = fas("bolt")
  def bomb = fas("bomb")
  def book = fas("book")
  def bookmark = fas("bookmark")
  def bookmarkO = fas("bookmark-o")
  def briefcase = fas("briefcase")
  def btc = fas("btc")
  def bug = fas("bug")
  def building = fas("building")
  def buildingO = fas("building-o")
  def bullhorn = fas("bullhorn")
  def bullseye = fas("bullseye")
  def bus = fas("bus")
  def buysellads = fas("buysellads")
  def cab = fas("cab")
  def calculator = fas("calculator")
  def calendar = fas("calendar")
  def calendarO = fas("calendar-o")
  def camera = fas("camera")
  def cameraRetro = fas("camera-retro")
  def car = fas("car")
  def caretDown = fas("caret-down")
  def caretLeft = fas("caret-left")
  def caretRight = fas("caret-right")
  def caretSquareODown = fas("caret-square-o-down")
  def caretSquareOLeft = fas("caret-square-o-left")
  def caretSquareORight = fas("caret-square-o-right")
  def caretSquareOUp = fas("caret-square-o-up")
  def caretUp = fas("caret-up")
  def cartArrowDown = fas("cart-arrow-down")
  def cartPlus = fas("cart-plus")
  def cc = fas("cc")
  def ccAmex = fas("cc-amex")
  def ccDiscover = fas("cc-discover")
  def ccMastercard = fas("cc-mastercard")
  def ccPaypal = fas("cc-paypal")
  def ccStripe = fas("cc-stripe")
  def ccVisa = fas("cc-visa")
  def certificate = fas("certificate")
  def chain = fas("chain")
  def chainBroken = fas("chain-broken")
  def check = fas("check")
  def checkCircle = fas("check-circle")
  def checkCircleO = fas("check-circle-o")
  def checkSquare = fas("check-square")
  def checkSquareO = fas("check-square-o")
  def chevronCircleDown = fas("chevron-circle-down")
  def chevronCircleLeft = fas("chevron-circle-left")
  def chevronCircleRight = fas("chevron-circle-right")
  def chevronCircleUp = fas("chevron-circle-up")
  def chevronDown = fas("chevron-down")
  def chevronLeft = fas("chevron-left")
  def chevronRight = fas("chevron-right")
  def chevronUp = fas("chevron-up")
  def child = fas("child")
  def circle = fas("circle")
  def circleO = fas("circle-o")
  def circleONotch = fas("circle-o-notch")
  def circleThin = fas("circle-thin")
  def clipboard = fas("clipboard")
  def clockO = fas("clock-o")
  def close = fas("fas fa-times")
  def cloud = fas("cloud")
  def cloudDownload = fas("cloud-download")
  def cloudUpload = fas("cloud-upload")
  def cny = fas("cny")
  def code = fas("code")
  def codeFork = fas("code-fork")
  def codepen = fas("codepen")
  def coffee = fas("coffee")
  def cog = fas("cog")
  def cogs = fas("cogs")
  def columns = fas("columns")
  def comment = fas("comment")
  def commentO = fas("comment-o")
  def comments = fas("comments")
  def commentsO = fas("comments-o")
  def compass = fas("compass")
  def compress = fas("compress")
  def connectdevelop = fas("connectdevelop")
  def copy = fas("copy")
  def copyright = fas("copyright")
  def creditCard = fas("credit-card")
  def crop = fas("crop")
  def crosshairs = fas("crosshairs")
  def css3 = fas("css3")
  def cube = fas("cube")
  def cubes = fas("cubes")
  def cut = fas("cut")
  def cutlery = fas("cutlery")
  def dashboard = fas("dashboard")
  def dashcube = fas("dashcube")
  def database = fas("database")
  def dedent = fas("dedent")
  def delicious = fas("delicious")
  def desktop = fas("desktop")
  def deviantart = fas("deviantart")
  def diamond = fas("diamond")
  def digg = fas("digg")
  def dollar = fas("dollar")
  def dotCircleO = fas("dot-circle-o")
  def download = fas("download")
  def dribbble = fas("dribbble")
  def dropbox = fas("dropbox")
  def drupal = fas("drupal")
  def edit = fas("edit")
  def eject = fas("eject")
  def ellipsisH = fas("ellipsis-h")
  def ellipsisV = fas("ellipsis-v")
  def empire = fas("empire")
  def envelope = fas("envelope")
  def envelopeO = fas("envelope-o")
  def envelopeSquare = fas("envelope-square")
  def eraser = fas("eraser")
  def eur = fas("eur")
  def euro = fas("euro")
  def exchange = fas("exchange")
  def exclamation = fas("exclamation")
  def exclamationCircle = fas("exclamation-circle")
  def exclamationTriangle = fas("exclamation-triangle")
  def expand = fas("expand")
  def externalLink = fas("external-link")
  def externalLinkSquare = fas("external-link-square")
  def eye = fas("eye")
  def eyeSlash = fas("eye-slash")
  def eyedropper = fas("eyedropper")
  def facebook = fas("facebook")
  def facebookF = fas("facebook-f")
  def facebookOfficial = fas("facebook-official")
  def facebookSquare = fas("facebook-square")
  def fastBackward = fas("fast-backward")
  def fastForward = fas("fast-forward")
  def fax = fas("-fax")
  def female = fas("female")
  def fighterJet = fas("fighter-jet")
  def file = fas("file")
  def fileArchiveO = fas("file-archive-o")
  def fileAudioO = fas("file-audio-o")
  def fileCodeO = fas("file-code-o")
  def fileExcelO = fas("file-excel-o")
  def fileImageO = fas("file-image-o")
  def fileMovieO = fas("file-movie-o")
  def fileO = fas("file-o")
  def filePdfO = fas("file-pdf-o")
  def filePhotoO = fas("file-photo-o")
  def filePictureO = fas("file-picture-o")
  def filePowerpointO = fas("file-powerpoint-o")
  def fileSoundO = fas("file-sound-o")
  def fileText = fas("file-text")
  def fileTextO = fas("file-text-o")
  def fileVideoO = fas("file-video-o")
  def fileWordO = fas("file-word-o")
  def fileZipO = fas("file-zip-o")
  def filesO = fas("files-o")
  def film = fas("film")
  def filter = fas("filter")
  def fire = fas("fire")
  def fireExtinguisher = fas("fire-extinguisher")
  def flag = fas("flag")
  def flagCheckered = fas("flag-checkered")
  def flagO = fas("flag-o")
  def flash = fas("flash")
  def flask = fas("flask")
  def flickr = fas("flickr")
  def floppyO = fas("floppy-o")
  def folder = fas("folder")
  def folderO = fas("folder-o")
  def folderOpen = fas("folder-open")
  def folderOpenO = fas("folder-open-o")
  def font = fas("font")
  def forumbee = fas("forumbee")
  def forward = fas("forward")
  def foursquare = fas("foursquare")
  def frownO = fas("frown-o")
  def futbolO = fas("futbol-o")
  def gamepad = fas("gamepad")
  def gavel = fas("gavel")
  def gbp = fas("gbp")
  def ge = fas("ge")
  def gear = fas("gear")
  def gears = fas("gears")
  def genderless = fas("genderless")
  def gift = fas("gift")
  def git = fas("git")
  def gitSquare = fas("git-square")
  def github = fas("github")
  def githubAlt = fas("github-alt")
  def githubSquare = fas("github-square")
  def gittip = fas("gittip")
  def glass = fas("glass")
  def globe = fas("globe")
  def google = fas("google")
  def googlePlus = fas("google-plus")
  def googlePlusSquare = fas("google-plus-square")
  def googleWallet = fas("google-wallet")
  def graduationCap = fas("graduation-cap")
  def gratipay = fas("gratipay")
  def group = fas("group")
  def hSquare = fas("h-square")
  def hackerNews = fas("hacker-news")
  def handODown = fas("hand-o-down")
  def handOLeft = fas("hand-o-left")
  def handORight = fas("hand-o-right")
  def handOUp = fas("hand-o-up")
  def hddO = fas("hdd-o")
  def header = fas("header")
  def headphones = fas("headphones")
  def heart = fas("heart")
  def heartO = fas("heart-o")
  def heartbeat = fas("heartbeat")
  def history = fas("history")
  def home = fas("home")
  def hospitalO = fas("hospital-o")
  def hotel = fas("hotel")
  def html5 = fas("html5")
  def ils = fas("ils")
  def image = fas("image")
  def inbox = fas("inbox")
  def indent = fas("indent")
  def info = fas("info")
  def infoCircle = fas("info-circle")
  def inr = fas("inr")
  def instagram = fas("instagram")
  def institution = fas("institution")
  def ioxhost = fas("ioxhost")
  def italic = fas("italic")
  def joomla = fas("joomla")
  def jpy = fas("jpy")
  def jsfiddle = fas("jsfiddle")
  def key = fas("key")
  def keyboardO = fas("keyboard-o")
  def krw = fas("krw")
  def language = fas("language")
  def laptop = fas("laptop")
  def lastfm = fas("lastfm")
  def lastfmSquare = fas("lastfm-square")
  def leaf = fas("leaf")
  def leanpub = fas("leanpub")
  def legal = fas("legal")
  def lemonO = fas("lemon-o")
  def levelDown = fas("level-down")
  def levelUp = fas("level-up")
  def lifeBouy = fas("life-bouy")
  def lifeBuoy = fas("life-buoy")
  def lifeRing = fas("life-ring")
  def lifeSaver = fas("life-saver")
  def lightbulbO = fas("lightbulb-o")
  def lineChart = fas("line-chart")
  def link = fas("link")
  def linkedin = fas("linkedin")
  def linkedinSquare = fas("linkedin-square")
  def linux = fas("linux")
  def list = fas("list")
  def listAlt = fas("list-alt")
  def listOl = fas("list-ol")
  def listUl = fas("list-ul")
  def locationArrow = fas("location-arrow")
  def lock = fas("lock")
  def longArrowDown = fas("long-arrow-down")
  def longArrowLeft = fas("long-arrow-left")
  def longArrowRight = fas("long-arrow-right")
  def longArrowUp = fas("long-arrow-up")
  def magic = fas("magic")
  def magnet = fas("magnet")
  def mailForward = fas("mail-forward")
  def mailReply = fas("mail-reply")
  def mailReplyAll = fas("mail-reply-all")
  def male = fas("male")
  def mapMarker = fas("map-marker")
  def mars = fas("mars")
  def marsDouble = fas("mars-double")
  def marsStroke = fas("mars-stroke")
  def marsStrokeH = fas("mars-stroke-h")
  def marsStrokeV = fas("mars-stroke-v")
  def maxcdn = fas("maxcdn")
  def meanpath = fas("meanpath")
  def medium = fas("medium")
  def medkit = fas("medkit")
  def mehO = fas("meh-o")
  def mercury = fas("mercury")
  def microphone = fas("microphone")
  def microphoneSlash = fas("microphone-slash")
  def minus = fas("minus")
  def minusCircle = fas("minus-circle")
  def minusSquare = fas("minus-square")
  def minusSquareO = fas("minus-square-o")
  def mobile = fas("mobile")
  def mobilePhone = fas("mobile-phone")
  def money = fas("money")
  def moonO = fas("moon-o")
  def mortarBoard = fas("mortar-board")
  def motorcycle = fas("motorcycle")
  def music = fas("music")
  def navicon = fas("navicon")
  def neuter = fas("neuter")
  def newspaperO = fas("newspaper-o")
  def openid = fas("openid")
  def outdent = fas("outdent")
  def pagelines = fas("pagelines")
  def paintBrush = fas("paint-brush")
  def paperPlane = fas("paper-plane")
  def paperPlaneO = fas("paper-plane-o")
  def paperclip = fas("paperclip")
  def paragraph = fas("paragraph")
  def paste = fas("paste")
  def pause = fas("pause")
  def paw = fas("paw")
  def paypal = fas("paypal")
  def pencil = fas("pencil")
  def pencilSquare = fas("pencil-square")
  def pencilSquareO = fas("pencil-square-o")
  def phone = fas("phone")
  def phoneSquare = fas("phone-square")
  def photo = fas("photo")
  def pictureO = fas("picture-o")
  def pieChart = fas("pie-chart")
  def piedPiper = fas("pied-piper")
  def piedPiperAlt = fas("pied-piper-alt")
  def pinterest = fas("pinterest")
  def pinterestP = fas("pinterest-p")
  def pinterestSquare = fas("pinterest-square")
  def plane = fas("plane")
  def play = fas("play")
  def playCircle = fas("play-circle")
  def playCircleO = fas("play-circle-o")
  def plug = fas("plug")
  def plus = fas("plus")
  def plusCircle = fas("plus-circle")
  def plusSquare = fas("plus-square")
  def plusSquareO = fas("plus-square-o")
  def powerOff = fas("power-off")
  def print = fas("print")
  def puzzlePiece = fas("puzzle-piece")
  def qq = fas("qq")
  def qrcode = fas("qrcode")
  def question = fas("question")
  def questionCircle = fas("question-circle")
  def quoteLeft = fas("quote-left")
  def quoteRight = fas("quote-right")
  def ra = fas("ra")
  def random = fas("random")
  def rebel = fas("rebel")
  def recycle = fas("recycle")
  def reddit = fas("reddit")
  def redditSquare = fas("reddit-square")
  def refresh = fas("refresh")
  def remove = fas("remove")
  def renren = fas("renren")
  def reorder = fas("reorder")
  def repeat = fas("repeat")
  def reply = fas("reply")
  def replyAll = fas("reply-all")
  def retweet = fas("retweet")
  def rmb = fas("rmb")
  def road = fas("road")
  def rocket = fas("rocket")
  def rotateLeft = fas("rotate-left")
  def rotateRight = fas("rotate-right")
  def rouble = fas("rouble")
  def rss = fas("rss")
  def rssSquare = fas("rss-square")
  def rub = fas("rub")
  def ruble = fas("ruble")
  def rupee = fas("rupee")
  def save = fas("save")
  def scissors = fas("scissors")
  def search = fas("search")
  def searchMinus = fas("search-minus")
  def searchPlus = fas("search-plus")
  def sellsy = fas("sellsy")
  def send = fas("send")
  def sendO = fas("send-o")
  def server = fas("server")
  def share = fas("share")
  def shareAlt = fas("share-alt")
  def shareAltSquare = fas("share-alt-square")
  def shareSquare = fas("share-square")
  def shareSquareO = fas("share-square-o")
  def shekel = fas("shekel")
  def sheqel = fas("sheqel")
  def shield = fas("shield")
  def ship = fas("ship")
  def shirtsinbulk = fas("shirtsinbulk")
  def shoppingCart = fas("shopping-cart")
  def signIn = fas("sign-in")
  def signOut = fas("sign-out")
  def signal = fas("signal")
  def simplybuilt = fas("simplybuilt")
  def sitemap = fas("sitemap")
  def skyatlas = fas("skyatlas")
  def skype = fas("skype")
  def slack = fas("slack")
  def sliders = fas("sliders")
  def slideshare = fas("slideshare")
  def smileO = fas("smile-o")
  def soccerBallO = fas("soccer-ball-o")
  def sort = fas("sort")
  def sortAlphaAsc = fas("sort-alpha-asc")
  def sortAlphaDesc = fas("sort-alpha-desc")
  def sortAmountAsc = fas("sort-amount-asc")
  def sortAmountDesc = fas("sort-amount-desc")
  def sortAsc = fas("sort-asc")
  def sortDesc = fas("sort-desc")
  def sortDown = fas("sort-down")
  def sortNumericAsc = fas("sort-numeric-asc")
  def sortNumericDesc = fas("sort-numeric-desc")
  def sortUp = fas("sort-up")
  def soundcloud = fas("soundcloud")
  def spaceShuttle = fas("space-shuttle")
  def spinner = fas("spinner")
  def spoon = fas("spoon")
  def spotify = fas("spotify")
  def square = fas("square")
  def squareO = fas("square-o")
  def stackExchange = fas("stack-exchange")
  def stackOverflow = fas("stack-overflow")
  def star = fas("star")
  def starHalf = fas("star-half")
  def starHalfEmpty = fas("star-half-empty")
  def starHalfFull = fas("star-half-full")
  def starHalfO = fas("star-half-o")
  def starO = fas("star-o")
  def steam = fas("steam")
  def steamSquare = fas("steam-square")
  def stepBackward = fas("step-backward")
  def stepForward = fas("step-forward")
  def stethoscope = fas("stethoscope")
  def stop = fas("stop")
  def streetView = fas("street-view")
  def strikethrough = fas("strikethrough")
  def stumbleupon = fas("stumbleupon")
  def stumbleuponCircle = fas("stumbleupon-circle")
  def subscript = fas("subscript")
  def subway = fas("subway")
  def suitcase = fas("suitcase")
  def sunO = fas("sun-o")
  def superscript = fas("superscript")
  def support = fas("support")
  def table = fas("table")
  def tablet = fas("tablet")
  def tachometer = fas("tachometer")
  def tag = fas("tag")
  def tags = fas("tags")
  def tasks = fas("tasks")
  def taxi = fas("taxi")
  def tencentWeibo = fas("tencent-weibo")
  def terminal = fas("terminal")
  def textHeight = fas("text-height")
  def textWidth = fas("text-width")
  def th = fas("th")
  def thLarge = fas("th-large")
  def thList = fas("th-list")
  def thumbTack = fas("thumb-tack")
  def thumbsDown = fas("thumbs-down")
  def thumbsODown = fas("thumbs-o-down")
  def thumbsOUp = fas("thumbs-o-up")
  def thumbsUp = fas("thumbs-up")
  def ticket = fas("ticket")
  def times = fas("times")
  def timesCircle = fas("times-circle")
  def timesCircleO = fas("times-circle-o")
  def tint = fas("tint")
  def toggleDown = fas("toggle-down")
  def toggleLeft = fas("toggle-left")
  def toggleOff = fas("toggle-off")
  def toggleOn = fas("toggle-on")
  def toggleRight = fas("toggle-right")
  def toggleUp = fas("toggle-up")
  def train = fas("train")
  def transgender = fas("transgender")
  def transgenderAlt = fas("transgender-alt")
  def trash = fas("trash")
  def trashO = fas("trash-o")
  def tree = fas("tree")
  def trello = fas("trello")
  def trophy = fas("trophy")
  def truck = fas("truck")
  def `try` = fas("try")
  def tty = fas("tty")
  def tumblr = fas("tumblr")
  def tumblrSquare = fas("tumblr-square")
  def turkishLira = fas("turkish-lira")
  def twitch = fas("twitch")
  def twitter = fas("twitter")
  def twitterSquare = fas("twitter-square")
  def umbrella = fas("umbrella")
  def underline = fas("underline")
  def undo = fas("undo")
  def university = fas("university")
  def unlink = fas("unlink")
  def unlock = fas("unlock")
  def unlockAlt = fas("unlock-alt")
  def unsorted = fas("unsorted")
  def upload = fas("upload")
  def usd = fas("usd")
  def user = fas("user")
  def userMd = fas("user-md")
  def userPlus = fas("user-plus")
  def userSecret = fas("user-secret")
  def userTimes = fas("user-times")
  def users = fas("users")
  def venus = fas("venus")
  def venusDouble = fas("venus-double")
  def venusMars = fas("venus-mars")
  def viacoin = fas("viacoin")
  def videoCamera = fas("video-camera")
  def vimeoSquare = fas("vimeo-square")
  def vine = fas("vine")
  def vk = fas("vk")
  def volumeDown = fas("volume-down")
  def volumeOff = fas("volume-off")
  def volumeUp = fas("volume-up")
  def warning = fas("warning")
  def wechat = fas("wechat")
  def weibo = fas("weibo")
  def weixin = fas("weixin")
  def whatsapp = fas("whatsapp")
  def wheelchair = fas("wheelchair")
  def wifi = fas("wifi")
  def windows = fas("windows")
  def won = fas("won")
  def wordpress = fas("wordpress")
  def wrench = fas("wrench")
  def xing = fas("xing")
  def xingSquare = fas("xing-square")
  def yahoo = fas("yahoo")
  def yelp = fas("yelp")
  def yen = fas("yen")
  def youtube = fas("youtube")
  def youtubePlay = fas("youtube-play")
  def youtubeSquare = fas("youtube-square")

  def windowMaximize = fas("window-maximize")
  def windowClose = fas("window-close")
  def windowCloseO = fas("window-close-o")
}

